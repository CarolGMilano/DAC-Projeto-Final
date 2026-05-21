require("dotenv-safe").config();

const jwt = require('jsonwebtoken');
const http = require('http');
const express = require('express');
const httpProxy = require('express-http-proxy');
const logger = require('morgan');
const helmet = require('helmet');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();

const corsOptions = {
  origin: "http://localhost:4200",
  methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
  allowedHeaders: ["Content-Type", "Authorization"]
};

app.use(cors(corsOptions));

app.use(logger('dev'));
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

//Microsserviços
const authServiceProxy = httpProxy('http://localhost:5000', {
  userResDecorator: function(respostaMsauth, corpoResposta, requisicaoCliente, respostaCliente) {
    //Transforma o body binário em texto
    const respostaTexto = Buffer.from(corpoResposta).toString('utf-8');

    //Copia o status do msauth pro cliente
    respostaCliente.status(respostaMsauth.statusCode);

    //Tenta converter a resposta para JSON
    try {
      return JSON.parse(respostaTexto);
    } catch {
      //Se não for JSON, retorna texto
      return respostaTexto;
    }
  }
});

const contaServiceProxy = httpProxy('http://localhost:8081');
const clienteServiceProxy = httpProxy('http://localhost:8082');
const gerenteServiceProxy = httpProxy('http://localhost:8083');

//Sagas
const sagaServiceProxy = httpProxy('http://localhost:8084');

function validacaoToken(req, res, next) {
  const headerRequisicao = req.headers.authorization;

  if (!headerRequisicao) {
    return res.status(401).json({ message: "Token não enviado" });
  }

  const token = headerRequisicao.split(" ")[1];

  try {
    jwt.verify(token, process.env.SECRET);
    next();
  } catch (err) {
    return res.status(401).json({ message: "Token inválido ou expirado" });
  }
}

//Auth
app.post('/login', authServiceProxy);
app.post("/logout", validacaoToken, async (req, res) => {
  try {
    const token = req.headers.authorization?.split(" ")[1];
    const payload = jwt.decode(token);

    //sub do JWT é o email
    const email = payload.sub;

    const usuarioResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);
    const usuario = await usuarioResp.json();

    const gerenteResp = await fetch(`http://localhost:8083/gerentes/usuario/${usuario.id}`);
    const gerente = await gerenteResp.json();

    return res.status(200).json({
      nome: gerente.nome,
      cpf: gerente.cpf,
      email: usuario.email,
      tipo: usuario.tipo
    });
  } catch (err) {
    console.error(err);
    return res.status(500).json({
      message: "Erro na API Composition logout",
      error: err.message
    });
  }
});

//Listar gerentes
app.get("/gerentes", validacaoToken, async (req, res) => {
  try {
    const usuariosResp = await fetch("http://localhost:5000/auth/usuarios/funcionarios");
    const gerentesResp = await fetch("http://localhost:8083/gerentes");

    const usuarios = await usuariosResp.json();
    const gerentes = await gerentesResp.json();

    const resultado = gerentes.map(gerente => {
      const usuario = usuarios.find(usuario =>
        String(usuario.id) === String(gerente.idUsuario)
      );

      return {
        nome: gerente.nome,
        cpf: gerente.cpf,
        email: usuario?.email,
        tipo: usuario?.tipo
      };
    });

    return res.json(resultado);

  } catch (err) {
    return res.status(500).json({
      message: "Erro na API Composition",
      error: err.message
    });
  }
});

//Todas as alterações realizadas que usam SAGA precisam esperar a resposta, por isso usa-se o get pra todas elas.
app.post('/gerentes', validacaoToken, sagaServiceProxy);
app.get('/gerentes/status/:id', validacaoToken, sagaServiceProxy);

app.put('/gerentes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;
    const { nome, email, senha } = req.body;

    const gerenteBusca = await fetch(`http://localhost:8083/gerentes/${cpf}`);

    if (!gerenteBusca.ok) {
      const erro = await gerenteBusca.text();

      return res
        .status(gerenteBusca.status)
        .send(erro);
    }

    const gerente = await gerenteBusca.json();
    const idUsuario = gerente.idUsuario;

    //console.log("STATUS AUTH:", idUsuario);

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios`,
      {
        method:'PUT',
        headers:{
          'Content-Type':'application/json',
          Authorization:req.headers.authorization
        },
        body:JSON.stringify({
          id: idUsuario,
          email,
          senha
        })
      }
    );

    //console.log("STATUS AUTH:", authResp.status);

    if (!authResp.ok) {
      const erroTexto = await authResp.text();
      console.log("ERRO AUTH:", erroTexto);
      try {
        return res
          .status(authResp.status)
          .json(JSON.parse(erroTexto));
      } catch {
        return res
          .status(authResp.status)
          .send(erroTexto);
      }
    }

    const gerenteResp = await fetch(
      `http://localhost:8083/gerentes/${cpf}`,
      {
        method:'PUT',
        headers:{
          'Content-Type':'application/json',
          Authorization:req.headers.authorization
        },
        body:JSON.stringify({
          nome
        })
      }
    );

    if (!gerenteResp.ok) {
      const erro = await gerenteResp.text();

      return res
        .status(gerenteResp.status)
        .send(erro);
    }

    const authAtualizado = await authResp.json();
    const gerenteAtualizado = await gerenteResp.json();

    return res.status(200).json({
      mensagem:'Gerente atualizado com sucesso',
      auth: authAtualizado,
      gerente: gerenteAtualizado
    });
  }
  catch(err){
    console.error(err);

    return res.status(500).json({
      erro:'Erro interno no gateway',
      detalhe: err.message
    });

  }
});
app.delete('/gerentes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;

    const gerenteBusca = await fetch(`http://localhost:8083/gerentes/${cpf}`);

    if (!gerenteBusca.ok) {
      const erro = await gerenteBusca.text();

      return res
        .status(gerenteBusca.status)
        .send(erro);
    }

    const gerente = await gerenteBusca.json();
    const idUsuario = gerente.idUsuario;

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios/desativar`,
      {
        method:'PUT',
        headers:{
          'Content-Type':'application/json',
          Authorization:req.headers.authorization
        },
        body: JSON.stringify({
          id: idUsuario
        })
      }
    );

    //console.log("STATUS AUTH:", authResp.status);
    //console.log("BODY AUTH:", await authResp.text());

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res
        .status(authResp.status)
        .send(erro);
    }

    const gerenteResp = await fetch(
      `http://localhost:8083/gerentes/${cpf}`,
      {
        method:'DELETE',
        headers:{
          'Content-Type':'application/json',
          Authorization:req.headers.authorization
        }
      }
    );

    if (!gerenteResp.ok) {
      const erro = await gerenteResp.text();

      return res
        .status(gerenteResp.status)
        .send(erro);
    }

    return res.status(200).json({
      mensagem:'Gerente inativado com sucesso'
    });
  }
  catch(err){
    console.error(err);

    return res.status(500).json({
      erro:'Erro interno no gateway',
      detalhe: err.message
    });
  }
});

app.get('/gerentes/:cpf', validacaoToken, gerenteServiceProxy);


// ====================
// Cliente
// ====================

// R1 - Autocadastro (sem token )
app.post('/clientes', async (req, res) => {
  try {
    const clienteResp = await fetch('http://localhost:8082/clientes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(req.body)
    });
    const data = await clienteResp.json();
    return res.status(clienteResp.status).json(data);
  } catch (err) {
    return res.status(500).json({ message: "Erro no autocadastro", error: err.message });
  }
});

//Listagem com filtros
app.get('/clientes', validacaoToken, clienteServiceProxy);

//API Composition: ms-cliente + ms-conta + ms-gerente
app.get('/clientes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;

    
    const clienteResp = await fetch(`http://localhost:8082/clientes/${cpf}`);
    if (!clienteResp.ok) {
      return res.status(clienteResp.status).json(await clienteResp.json());
    }
    const cliente = await clienteResp.json();

  
    const contaResp = await fetch(`http://localhost:8081/contas/cliente/${cpf}`);
    const conta = contaResp.ok ? await contaResp.json() : null;

   
    let gerenteNome = null;
    let gerenteEmail = null;
    if (cliente.idGerente) {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/id/${cliente.idGerente}`);
      if (gerenteResp.ok) {
        const gerente = await gerenteResp.json();
        gerenteNome  = gerente.nome;
        gerenteEmail = gerente.email;
      }
    }

     //Swagger DadosClienteResponse
    return res.status(200).json({
      cpf:           cliente.cpf,
      nome:          cliente.nome,
      email:         cliente.email,
      telefone:      cliente.telefone,
      endereco:      cliente.endereco?.logradouro,
      cidade:        cliente.endereco?.cidade,
      estado:        cliente.endereco?.estado,
      salario:       cliente.salario,
      conta:         conta?.numero,
      saldo:         conta?.saldo,
      limite:        conta?.limite,
      gerente:       conta?.gerente,
      gerente_nome:  gerenteNome,
      gerente_email: gerenteEmail
    });
  } catch (err) {
    return res.status(500).json({ message: "Erro ao consultar cliente", error: err.message });
  }
});

// R4 - Alterar perfil
app.put('/clientes/:cpf', validacaoToken, clienteServiceProxy);
// R10 - Aprovar cliente
app.post('/clientes/:cpf/aprovar', validacaoToken, clienteServiceProxy);
// R11 - Rejeitar cliente
app.post('/clientes/:cpf/rejeitar', validacaoToken, clienteServiceProxy);


// ====================
// Conta
// ====================

// Command
app.post('/contas', contaServiceProxy);
app.put('/contas/:numeroConta', contaServiceProxy);
app.delete('/contas/:numeroConta', contaServiceProxy);

// Query
app.get('/clientes/', contaServiceProxy);
app.get('/clientes/:cpf', contaServiceProxy);
app.get('/contas/:numero/saldo', contaServiceProxy);
app.get('/contas/:numero/extrato', contaServiceProxy);
//app.get('/gerentes', contaServiceProxy);

var server = http.createServer(app);

server.listen(3000, () => {
  console.log("API Gateway rodando na porta 3000");
});