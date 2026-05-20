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
    const payload =
      jwt.verify(
        token,
        process.env.SECRET
      );

    req.usuario = payload;

    next();
  } catch (err) {
    return res.status(401).json({ message: "Token inválido ou expirado" });
  }
}

/*
  Esses são endpoints que precisam ser implementadas, todas as outras serão usadas dentro dessas para lógicas.

  get/reboot

  post/login
  post/logout

  get/clientes
  post/clientes
  get/clientes/{cpf}
  put/clientes/{cpf}
  post/clientes/{cpf}/aprovar
  post/clientes/{cpf}/rejeitar

  get/contas/{numero}/saldo
  post/contas/{numero}/depositar
  post/contas/{numero}/sacar
  post/contas/{numero}/transferir
  get/contas/{numero}/extrato

  get/gerentes
  post/gerentes
  get/gerentes/{cpf}
  delete/gerentes/{cpf}
  put/gerentes/{cpf}
*/


/* 
  //Reboot
  app.post('/reboot/gerentes', sagaServiceProxy);
  app.get('/reboot/gerentes/status/:id', sagaServiceProxy);
  app.get("/reboot", async(req,res)=>{
    try{
      await fetch("http://auth:5000/reboot",{
        method:"DELETE"
      });

      await fetch("http://gerente:3000/reboot",{
        method:"DELETE"
      });

      const gerentes = [
        {
          nome:"Carol",
          cpf:"12345678910",
          email:"admin0@bantads.com.br",
          senha:"tads",
          tipo:"ADMIN"
        },
        {
          nome:"Adamântio",
          cpf:"40501740066",
          email:"admin1@bantads.com.br",
          senha:"tads",
          tipo:"ADMIN"
        },
        {
          nome:"Geniéve",
          cpf:"98574307084",
          email:"ger1@bantads.com.br",
          senha:"tads",
          tipo:"GERENTE"
        },
        {
          nome:"Godophredo",
          cpf:"64065268052",
          email:"ger2@bantads.com.br",
          senha:"tads",
          tipo:"GERENTE"
        },
        {
          nome:"Gyândula",
          cpf:"23862179060",
          email:"ger3@bantads.com.br",
          senha:"tads",
          tipo:"GERENTE"
        }
      ];

      for(const gerente of gerentes){
        const response = await fetch(
          "http://localhost:3000/reboot/gerentes",
          {
            method:"POST",
            headers:{
              "Content-Type":"application/json"
            },
            body:JSON.stringify(gerente)
          }
        );

        const body = await response.json();
        const sagaId = body.id;
        let status = "PENDENTE";

        while(status === "PENDENTE"){
          await new Promise(r=>setTimeout(r,1000));

          const statusResponse = await fetch(`http://localhost:3000/reboot/gerentes/status/${sagaId}`);
          const statusBody = await statusResponse.json();

          status = statusBody.status;
        }

        if(status !== "SUCCESSO"){
          throw new Error(
            `Falha ao criar ${gerente.email}`
          );
        }
      }

      res.json({
        message:"Reboot concluído"
      });
    }catch(err){
      res.status(500).json({
        erro:err.message
      });
    }
  });
*/

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


//Cliente
app.get('/clientes', validacaoToken, async (req,res)=>{
  try{
    const filtro = req.query.filtro;

    let urlClientes = "http://localhost:8082/clientes";

    if(filtro){
      urlClientes += `?filtro=${filtro}`;
    }

    const [
      clientesResp,
      usuariosResp,
      gerentesResp
    ] = await Promise.all([
      fetch(urlClientes),
      fetch("http://localhost:5000/auth/usuarios"),
      fetch("http://localhost:8083/gerentes")
    ]);

    const clientes = await clientesResp.json();
    const usuarios = await usuariosResp.json();
    const gerentes = await gerentesResp.json();

    const emailLogado = req.usuario.sub;

    const usuarioLogado =
      usuarios.find(usuario =>
        usuario.email === emailLogado
      );

    const gerenteLogado =
      gerentes.find(gerente =>
        String(gerente.idUsuario) === String(usuarioLogado?.id)
      );

    const cpfGerente = gerenteLogado?.cpf;

    if(filtro==="para_aprovar"){
      return res.json(
        clientes.filter(cliente =>
          String(cliente.cpfGerente) === String(cpfGerente)
        ).map(cliente=>{
          const usuario =  usuarios.find(usuario =>
            String(usuario.id) === String(cliente.idUsuario)
          );

          return{
            cpf:cliente.cpf,
            nome:cliente.nome,

            email:usuario?.email,

            salario:cliente.salario,
            endereco:cliente.endereco,
            cidade:cliente.cidade,
            estado:cliente.estado
          };
        })
      );
    }

    if(filtro==="adm_relatorio_clientes"){
      const contasResp = await fetch("http://localhost:8081/contas");
      const contas = await contasResp.json();

      const resultado = clientes.map(cliente=>{
        const usuarioCliente = usuarios.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.cpfCliente) === String(cliente.cpf)
        );

        const gerente = gerentes.find(gerente =>
          String(gerente.cpf) === String(conta?.cpfGerente)
        );

        const usuarioGerente = usuarios.find(usuario =>
          String(usuario.id) === String(gerente?.idUsuario)
        );

        return{
          cpf:cliente.cpf,
          nome:cliente.nome,
          telefone:cliente.telefone,
          endereco:cliente.endereco,
          cidade:cliente.cidade,
          estado:cliente.estado,
          salario:cliente.salario,

          email:usuarioCliente?.email,

          conta:conta?.numero,
          saldo:conta?.saldo,
          limite:conta?.limite,

          gerente:gerente?.cpf,
          gerente_nome:gerente?.nome,
          gerente_email:usuarioGerente?.email
        };
      });

      resultado.sort((a, b) =>
        a.nome.localeCompare(b.nome)
      );

      return res.json(resultado);
    }

    if(filtro==="melhores_clientes"){
      const contasResp = await fetch("http://localhost:8081/contas");

      const contas = await contasResp.json();

      const resultado = clientes.map(cliente => {
        const usuario = usuarios.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.cpfCliente) === String(cliente.cpf)
        );

        return{
          cpf:cliente.cpf,
          nome:cliente.nome,
          cidade:cliente.cidade,
          estado:cliente.estado,

          email:usuario?.email,

          conta:conta?.numero,
          saldo:conta?.saldo,
          limite:conta?.limite
        };
      });

      resultado.sort((a,b) =>
        (b.saldo ?? 0) - (a.saldo ?? 0)
      );

      return res.json(
        resultado.slice(0,3)
      );
    }

    const contasResp = await fetch("http://localhost:8081/contas");

    const contas = await contasResp.json();

    const resultado = clientes.map(cliente => {
      const usuario = usuarios.find(usuario =>
        String(usuario.id) === String(cliente.idUsuario)
      );

      const conta = contas.find(conta =>
        String(conta.cpfCliente) === String(cliente.cpf)
      );

      return{
        cpf:cliente.cpf,
        nome:cliente.nome,
        cidade:cliente.cidade,
        estado:cliente.estado,

        email:usuario?.email,

        conta:conta?.numero,
        saldo:conta?.saldo,
        limite:conta?.limite,

        cpfGerente: conta?.cpfGerente
      };
    }).filter(cliente =>
      String(cliente.cpfGerente) === String(cpfGerente)
    );

    resultado.sort((a,b) =>
      a.nome.localeCompare(b.nome)
    );

    return res.json(resultado);
  } catch(err) {
    return res.status(500).json({ message: "Erro na API Composition", error:err.message });
  }
});
app.post('/clientes', async (req, res) => {
  try {
    const {
      cpf,
      email,
      nome,
      telefone,
      salario,
      endereco,
      cep,
      cidade,
      estado
    } = req.body;

    const clienteExistenteResp = await fetch(`http://localhost:8082/clientes/cpf/${cpf}`);

    if (clienteExistenteResp.ok) {
      return res.status(409).json({
        message: "Cliente já cadastrado ou aguardando aprovação."
      });
    }

    const emailResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);

    if (emailResp.ok) {
      return res.status(409).json({
        message: "Cliente já cadastrado ou aguardando aprovação."
      });
    }

    //Essa roda precisa existir no MSContas e vai devolver apenas o CPF do gerente com menos contas atreladas
    const gerenteResp = await fetch("http://localhost:8081/contas/disponivel");

    if (!gerenteResp.ok) {
      const erro = await gerenteResp.text();

      return res.status(500).json({
        message: "Não foi possível selecionar gerente.",
        error: erro
      });
    }

    const { cpfGerente } = await gerenteResp.json();

    const authResp = await fetch(
      "http://localhost:5000/auth/usuarios",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email,
          tipo: "CLIENTE",
          ativo: "PENDENTE"
        })
      }
    );

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res.status(500).json({
        message: "Falha ao criar usuário auth.",
        error: erro
      });
    }

    const usuarioCriado = await authResp.json();

    const clienteResp = await fetch(
      "http://localhost:8082/clientes",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          cpf,
          nome,
          telefone,
          salario,
          endereco,
          cep,
          cidade,
          estado,
          idUsuario: usuarioCriado.id,
          cpfGerente
        })
      }
    );

    if (!clienteResp.ok) {
      await fetch(
        `http://localhost:5000/auth/usuarios/${usuarioCriado.id}`,
        { method: "DELETE" }
      );

      const erro = await clienteResp.text();

      return res.status(500).json({
        message: "Falha ao criar cliente.",
        error: erro
      });
    }

    return res.status(202).json({
      message: "Solicitação enviada para aprovação.",
      cpfGerente
    });

  } catch (err) {
    return res.status(500).json({
      message: "Erro no autocadastro.",
      error: err.message
    });
  }
});
app.get('/clientes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;

    const clienteResp = await fetch(`http://localhost:8082/clientes/cpf/${cpf}`);

    if (!clienteResp.ok) {
      const erro = await clienteResp.text();
      return res.status(clienteResp.status).send(erro);
    }

    const cliente = await clienteResp.json();

    const usuarioResp = await fetch(`http://localhost:5000/auth/usuarios/${cliente.idUsuario}`);

    let usuario = null;

    if (usuarioResp.ok) {
      usuario = await usuarioResp.json();
    }

    const contasResp = await fetch(`http://localhost:8081/contas`);

    const contas = await contasResp.json();

    const conta = contas.find(c =>
      String(c.cpfCliente) === String(cliente.cpf)
    );

    let gerente = null;
    let usuarioGerente = null;

    if (conta?.cpfGerente) {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/${conta.cpfGerente}`);

      if (gerenteResp.ok) {
        gerente = await gerenteResp.json();

        const usuarioGerenteResp = await fetch(`http://localhost:5000/auth/usuarios/${gerente.idUsuario}`);

        if (usuarioGerenteResp.ok) {
          usuarioGerente = await usuarioGerenteResp.json();
        }
      }
    }

    return res.json({
      cpf: cliente.cpf,
      nome: cliente.nome,
      telefone: cliente.telefone,
      email: usuario?.email,

      endereco: cliente.endereco,
      cidade: cliente.cidade,
      estado: cliente.estado,
      salario: cliente.salario,

      conta: conta?.numero,
      saldo: conta?.saldo,
      limite: conta?.limite,

      gerente: gerente?.cpf,
      gerente_nome: gerente?.nome,
      gerente_email: usuarioGerente?.email
    });

  } catch (err) {
    return res.status(500).json({
      message: "Erro na composição do cliente",
      error: err.message
    });
  }
});
app.put('/clientes/:cpf', validacaoToken, async (req, res) => {

});
app.post('/clientes/:cpf/aprovar', validacaoToken, async (req, res) => {

});
app.post('/clientes/:cpf/rejeitar', validacaoToken, async (req, res) => {

});


//Gerente
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

    const usuarioExistenteResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);

    if (usuarioExistenteResp.ok) {
      const usuarioExistente = await usuarioExistenteResp.json();

      if (String(usuarioExistente.id) !== String(idUsuario)) {
        return res.status(409).json({
          message:
            "Email já cadastrado. Tente novamente."
        });
      }
    }

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios`,
      {
        method: 'PUT',
        headers: {
          'Content-Type':'application/json',
          Authorization:req.headers.authorization
        },
        body: JSON.stringify({
          id:idUsuario,
          email,
          senha
        })
      }
    );

    if (!authResp.ok) {
      const erroTexto = await authResp.text();

      try {
        return res
          .status(authResp.status)
          .json(JSON.parse(erroTexto));
      }
      catch {
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
        body: JSON.stringify({
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
      cpf: gerenteAtualizado.cpf,
      nome: gerenteAtualizado.nome,
      email: authAtualizado.email,
      tipo: authAtualizado.tipo
    });
  }
  catch(err){
    console.error(err);

    return res.status(500).json({
      erro:'Erro interno no gateway',
      detalhe:err.message
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
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          id: idUsuario
        })
      }
    );

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res
        .status(authResp.status)
        .send(erro);
    }

    const redistribuicaoResp = await fetch(
      `http://localhost:8081/contas/redistribuir-gerente`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          cpfGerente: cpf
        })
      }
    );

    if (!redistribuicaoResp.ok) {
      const erro = await redistribuicaoResp.text();

      return res
        .status(redistribuicaoResp.status)
        .send(erro);
    }

    const gerenteResp = await fetch(
      `http://localhost:8083/gerentes/${cpf}`,
      {
        method: 'DELETE'
      }
    );

    if (!gerenteResp.ok) {
      const erro = await gerenteResp.text();

      return res
        .status(gerenteResp.status)
        .send(erro);
    }

    return res.status(200).json({
      mensagem: 'Gerente removido com sucesso',
      gerenteRemovido: cpf
    });
  } catch (err) {
    console.error(err);

    return res.status(500).json({
      erro: 'Erro interno no gateway',
      detalhe: err.message
    });
  }
});
app.get('/gerentes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;

    const [usuariosResp, gerentesResp] = await Promise.all([
      fetch("http://localhost:5000/auth/usuarios/funcionarios"),
      fetch("http://localhost:8083/gerentes")
    ]);

    const usuarios = await usuariosResp.json();
    const gerentes = await gerentesResp.json();

    const gerente = gerentes.find(g =>
      String(g.cpf) === String(cpf)
    );

    if (!gerente) {
      return res.status(404).json({
        message: "Gerente não encontrado"
      });
    }

    const usuario = usuarios.find(u =>
      String(u.id) === String(gerente.idUsuario)
    );

    const resultado = {
      nome: gerente.nome,
      cpf: gerente.cpf,
      email: usuario?.email,
      tipo: usuario?.tipo
    };

    return res.json(resultado);
  } catch (err) {

    return res.status(500).json({
      message: "Erro na API Composition",
      error: err.message
    });
  }
});


//Conta
app.get('/contas/:numero/saldo', validacaoToken, async (req, res) => {

});
app.post('/contas/:numero/depositar', validacaoToken, async (req, res) => {
  
});
app.post('/contas/:numero/sacar', validacaoToken, async (req, res) => {

});
app.post('/contas/:numero/transferir', validacaoToken, async (req, res) => {

});
app.get('/contas/:numero/extrato', validacaoToken, async (req, res) => {

});


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