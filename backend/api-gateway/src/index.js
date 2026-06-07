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

const contaQueryServiceProxy = httpProxy('http://localhost:8081');
const contaCommandServiceProxy = httpProxy('http://localhost:9090');

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

  //Verifica se está na blacklist
  if (blacklist.has(token)) {
    return res.status(401).json({ message: "Token inválido (logout)" });
  }

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

//Set é um tipo que guarda valores distinct. 
const blacklist = new Set();
 
//Reboot
app.get("/reboot", async(req,res)=>{
    try{
      const authReboot = await fetch(
        "http://localhost:5000/reboot",
        {
          method: "POST"
        }
      );

      if (!authReboot.ok) {
        const erroTexto = await authReboot.text();

        return res.status(authReboot.status).json({
          message: erroTexto
        });
      }
      
      const data = await authReboot.json();

      const usuarios = data.usuarios ?? data;
      
      const gerenteReboot = await fetch(
        "http://localhost:8083/gerentes/reboot",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(usuarios)
        }
      );

      if (!gerenteReboot.ok) {
        const erroTexto = await gerenteReboot.text();

        return res.status(gerenteReboot.status).json({
          message: erroTexto
        });
      }

      const clienteReboot = await fetch(
        "http://localhost:8082/clientes/reboot",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(usuarios)
        }
      );

      if (!clienteReboot.ok) {
        const erroTexto = await clienteReboot.text();

        return res.status(clienteReboot.status).json({
          message: erroTexto
        });
      }

      const contaReboot = await fetch(
        "http://localhost:9090/contas/reboot",
        {
          method: "POST"
        }
      );

      if (!contaReboot.ok) {
        const erroTexto = await contaReboot.text();

        return res.status(contaReboot.status).json({
          message: erroTexto
        });
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

//Auth
app.post('/login', async (req, res) => {
  try {
    const { login, senha } = req.body;

    const authResp = await fetch('http://localhost:5000/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        login,
        senha
      })
    });

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res
        .status(authResp.status)
        .json({ message: erro });
    }

    const auth = await authResp.json();

    const usuario = auth.usuario;
    let dadosPessoa;

    if (auth.tipo === 'CLIENTE') {
      const clienteResp = await fetch(`http://localhost:8082/clientes/usuario/${usuario.id}` );

      if (!clienteResp.ok) {
        return res.status(clienteResp.status).json({
          message: 'Erro ao buscar cliente'
        });
      }

      dadosPessoa = await clienteResp.json();
    } else if (auth.tipo === 'GERENTE' || auth.tipo === 'ADMINISTRADOR') {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/usuario/${usuario.id}`);

      if (!gerenteResp.ok) {
        return res.status(gerenteResp.status).json({
          message: 'Erro ao buscar gerente'
        });
      }

      dadosPessoa = await gerenteResp.json();
    }

    return res.json({
      access_token: auth.access_token,
      token_type: auth.token_type,
      tipo: auth.tipo,

      usuario: {
        cpf: dadosPessoa.cpf,
        nome: dadosPessoa.nome,
        email: usuario.email
      }
    });
  } catch (err) {
    return res.status(500).json({
      message: 'Erro na composição do login',
      error: err.message
    });
  }
});
app.post("/logout", validacaoToken, async (req, res) => {
  try {
    const token = req.headers.authorization?.split(" ")[1];
    const payload = jwt.decode(token);

    const email = payload.sub;

    const authResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);

    if (!authResp.ok) {
      const erro = await authResp.json();

      return res.status(authResp.status).json(erro);
    }

    const usuario = await authResp.json();

    let dados;

    if (usuario.tipo === "GERENTE" || usuario.tipo === "ADMINISTRADOR") {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/usuario/${usuario.id}`);

      if (!gerenteResp.ok) {
        const erro = await gerenteResp.json();

        return res.status(gerenteResp.status).json(erro);
      }

      dados = await gerenteResp.json();
    } else if (usuario.tipo === "CLIENTE") {
      const clienteResp = await fetch(`http://localhost:8082/clientes/usuario/${usuario.id}`);

      if (!clienteResp.ok) {
        const erro = await clienteResp.json();

        return res.status(clienteResp.status).json(erro);
      }

      dados = await clienteResp.json();
    }

    //Adiciona na blacklist
    blacklist.add(token);

    return res.status(200).json({
      nome: dados.nome,
      cpf: dados.cpf,
      email: usuario.email,
      tipo: usuario.tipo
    });
  } catch (erro) {
    console.error(erro);

    return res.status(500).json({
      message: "Erro na API Composition logout",
      error: erro.message
    });
  }
});

//Cliente
app.get('/clientes', validacaoToken, async (req,res)=>{
  try{
    const filtro = req.query.filtro;

    if (!filtro && req.usuario.tipo !== "GERENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    if (filtro === "para_aprovar" && req.usuario.tipo !== "GERENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    if (filtro === "melhores_clientes" && req.usuario.tipo !== "GERENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    if (filtro === "adm_relatorio_clientes" && req.usuario.tipo !== "ADMINISTRADOR") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    let urlClientes = "http://localhost:8082/clientes";
    let urlUsuariosC = "http://localhost:5000/auth/usuarios/clientes";

    if(filtro){
      urlClientes += `?filtro=${filtro}`;
      urlUsuariosC += `?filtro=${filtro}`;
    }

    const [
      clientesResp,
      usuariosCResp,
      usuariosFResp,
      gerentesResp
    ] = await Promise.all([
      fetch(urlClientes),
      fetch(urlUsuariosC),
      fetch("http://localhost:5000/auth/usuarios/funcionarios"),
      fetch("http://localhost:8083/gerentes")
    ]);

    if (!clientesResp.ok) {
      const erro = await clientesResp.json();

      return res.status(clientesResp.status).json(erro);
    }
    
    const clientes = await clientesResp.json();

    if (!usuariosCResp.ok) {
      const erro = await usuariosCResp.json();

      return res.status(usuariosCResp.status).json(erro);
    }
    
    const usuariosC = await usuariosCResp.json();
    
    if (!usuariosFResp.ok) {
      const erro = await usuariosFResp.json();

      return res.status(usuariosFResp.status).json(erro);
    }

    const usuariosF = await usuariosFResp.json();
    
    if (!gerentesResp.ok) {
      const erro = await gerentesResp.json();

      return res.status(gerentesResp.status).json(erro);
    }

    const gerentes = await gerentesResp.json();

    const emailLogado = req.usuario.sub;

    const usuarioLogado =
      usuariosF.find(usuario =>
        usuario.email === emailLogado
      );

    const gerenteLogado =
      gerentes.find(gerente =>
        String(gerente.idUsuario) === String(usuarioLogado?.id)
      );

    const cpfGerente = gerenteLogado?.cpf;

    if (filtro === "para_aprovar") {
      if (!cpfGerente) {
        return res.json([]);
      }

      const resultado = clientes.filter(cliente =>
          String(cliente.cpfGerente) === String(cpfGerente)
        ).map(cliente => {
        const usuario = usuariosC.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        return {
          cpf: cliente.cpf,
          nome: cliente.nome,
          email: usuario?.email,
          salario: cliente.salario,
          endereco: cliente.endereco,
          cidade: cliente.cidade,
          estado: cliente.estado
        };
      });

      return res.json(resultado);
    }

    if(filtro==="adm_relatorio_clientes"){
      const contasResp = await fetch("http://localhost:8081/contas");

      if (!contasResp.ok) {
        const erro = await contasResp.json();

        return res.status(contasResp.status).json(erro);
      }

      const contas = await contasResp.json();

      const resultado = clientes.map(cliente=>{
        const usuarioCliente = usuariosC.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.clienteCpf) === String(cliente.cpf)
        );

        const gerente = gerentes.find(gerente =>
          String(gerente.cpf) === String(conta?.gerenteCpf)
        );

        const usuarioGerente = usuariosF.find(usuario =>
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
        const usuario = usuariosC.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.clienteCpf) === String(cliente.cpf)
        );

        return{
          cpf:cliente.cpf,
          nome:cliente.nome,
          cidade:cliente.cidade,
          estado:cliente.estado,

          email:usuario?.email,

          conta:conta?.conta,
          saldo:conta?.saldo,
          limite:conta?.limite
        };
      });

      console.log(resultado.map(c => ({ cpf: c.cpf, saldo: c.saldo })));
      resultado.sort((a,b) =>
        (b.saldo ?? 0) - (a.saldo ?? 0)
      );

      return res.json(
        resultado.slice(0,3)
      );
    }

    const contasResp = await fetch("http://localhost:8081/contas");

    if (!contasResp.ok) {
      const erro = await contasResp.json();

      return res.status(contasResp.status).json(erro);
    }

    const contas = await contasResp.json();

    const resultado = clientes
      .map(cliente => {
        const usuario = usuariosC.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.clienteCpf) === String(cliente.cpf)
        );

        return {
          cpf: cliente.cpf,
          nome: cliente.nome,

          email: usuario?.email,

          telefone: cliente.telefone,
          endereco: cliente.endereco,
          cidade: cliente.cidade,
          estado: cliente.estado,

          conta: conta?.numero,
          saldo: conta?.saldo,
          limite: conta?.limite,

          //Só pro filtro interno
          cpfGerente: conta?.gerenteCpf
        };
      })
      .filter(cliente =>
        String(cliente.cpfGerente) === String(cpfGerente)
      )
      .map(({ cpfGerente, ...cliente }) => cliente);

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
      CEP,
      cidade,
      estado
    } = req.body;

    //Essa rota precisa existir no MSContas e vai devolver apenas o CPF do gerente com menos contas atreladas
    const contasResp = await fetch("http://localhost:8081/contas/disponivel");

    if (!contasResp.ok) {
      const erroTexto = await contasResp.text();

      return res.status(contasResp.status).json({
        message: erroTexto
      });
    }

    const cpfGerente = await contasResp.text();

    const inicio = await fetch(
      "http://localhost:8084/clientes",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          cpf,
          cpfGerente: cpfGerente,
          email,
          nome,
          telefone,
          salario,
          endereco,
          cep: CEP,
          cidade,
          estado
        })
      }
    );

    const bodyInicio = await inicio.json();

    const sagaId = bodyInicio.idSaga;

    const resultado = await verificarStatusSaga("clientes", sagaId);

    return res
      .status(resultado.statusCode)
      .json(resultado.body);

  } catch (e) {
    console.log(e);

    return res.status(500).json({
      erro: e.message,
      stack: e.stack
    });
  }
});
app.get('/clientes/:cpf', validacaoToken, async (req, res) => {
  try {
    const { cpf } = req.params;

    const filtro = req.query.filtro;

    const clienteResp = await fetch(`http://localhost:8082/clientes/${cpf}`);

    if (!clienteResp.ok) {
      const erro = await clienteResp.text();

      return res.status(clienteResp.status).json({
        message: erro
      });
    }

    const cliente = await clienteResp.json();

    const authResp = await fetch(`http://localhost:5000/auth/usuarios/${cliente.idUsuario}`);

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res.status(authResp.status).json({
        message: erro
      });
    }

    const usuario = await authResp.json();

    const contaResp = await fetch(`http://localhost:8081/contas/cliente/${cpf}`);

    if (!contaResp.ok) {
      const erro = await contaResp.text();

      return res.status(contaResp.status).json({
        message: erro
      });
    }

    const conta = await contaResp.json();
    let gerente = null;
    let usuarioGerente = null;
    
    if (cliente.cpfGerente) {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/${cliente.cpfGerente}`);
      
      if (!gerenteResp.ok) {
        const erro = await gerenteResp.text();
        
        return res.status(gerenteResp.status).json({
          message: erro
        });
      }
      
      gerente = await gerenteResp.json();
      
      const usuarioGerenteResp = await fetch(`http://localhost:5000/auth/usuarios/${gerente.idUsuario}`);
      
      if (!usuarioGerenteResp.ok) {
        const erro = await usuarioGerenteResp.text();
        
        return res.status(usuarioGerenteResp.status).json({
          message: erro
        });
      }

      usuarioGerente = await usuarioGerenteResp.json();
    }
    
    if(filtro){
      return res.json({
        cpf: cliente.cpf,
        nome: cliente.nome,
        telefone: cliente.telefone,
        email: usuario?.email,

        cep: cliente.cep,
        endereco: cliente.endereco,
        cidade: cliente.cidade,
        estado: cliente.estado,
        salario: cliente.salario,

        conta: conta?.conta,
        saldo: conta?.saldo,
        limite: conta?.limite,

        gerente: gerente?.cpf,
        gerente_nome: gerente?.nome,
        gerente_email: usuarioGerente?.email
      });
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

      conta: conta?.conta,
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
app.get('/clientes/usuario/:idUsuario', validacaoToken, async (req, res) => {
  try {
    const { idUsuario } = req.params;

    const clienteResp = await fetch(`http://localhost:8082/clientes/usuario/${idUsuario}`);

    if (!clienteResp.ok) {
      const erro = await clienteResp.text();

      return res.status(clienteResp.status).json({
        message: erro
      });
    }

    const cliente = await clienteResp.json();

    const authResp = await fetch(`http://localhost:5000/auth/usuarios/${cliente.idUsuario}`);

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res.status(authResp.status).json({
        message: erro
      });
    }

    const usuario = await authResp.json();

    
    const contaResp = await fetch(`http://localhost:8081/contas/cliente/${cpf}`);

    if (!contaResp.ok) {
      const erro = await contaResp.text();

      return res.status(contaResp.status).json({
        message: erro
      });
    }

    const conta = await contaResp.json();

    let gerente = null;
    let usuarioGerente = null;

    if (cliente.cpfGerente) {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/${cliente.cpfGerente}`);

      if (!gerenteResp.ok) {
        const erro = await gerenteResp.text();

        return res.status(gerenteResp.status).json({
          message: erro
        });
      }

      gerente = await gerenteResp.json();

      const usuarioGerenteResp = await fetch(`http://localhost:5000/auth/usuarios/${gerente.idUsuario}`);

      if (!usuarioGerenteResp.ok) {
        const erro = await usuarioGerenteResp.text();

        return res.status(usuarioGerenteResp.status).json({
          message: erro
        });
      }

      usuarioGerente = await usuarioGerenteResp.json();
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

      conta: conta?.conta,
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
  try {
    if (req.usuario.tipo !== "CLIENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    const { cpf } = req.params;

    const {
      nome,
      email,
      salario,
      endereco,
      CEP,
      cidade,
      estado
    } = req.body;

    /*
      Endpoint para buscar dados da conta do cliente, só para validar se existe conta para esse cliente.

      Pode retornar as seguintes informações se existir a conta para o cliente:
        conta
        saldo
        limite
    */
    const contaBusca = await fetch(`http://localhost:8081/contas/cliente/${cpf}`);

    if (!contaBusca.ok) {
      const erro = await contaBusca.text();

      return res.status(contaBusca.status).json({
        origem: 'SERVICO_CONTAS',
        message: erro
      });
    }
    const conta = await contaBusca.json();
    const numeroConta = conta.conta;

    const clienteBusca = await fetch(`http://localhost:8082/clientes/${cpf}`);

    if (!clienteBusca.ok) {
      const erro = await clienteBusca.text();

      return res.status(clienteBusca.status).json({
        origem: 'SERVICO_CLIENTE',
        message: erro
      });
    }

    const cliente = await clienteBusca.json();
    const idUsuario = cliente.idUsuario;

    const usuarioExistenteResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);

    if (usuarioExistenteResp.ok) {
      const usuarioExistente = await usuarioExistenteResp.json();

      //Verifica se o email duplicado é de outra pessoa
      if (String(usuarioExistente.id) !== String(idUsuario)) {
        return res.status(409).json({
          tipo: 'email',
          message: 'Email já cadastrado. Tente novamente.'
        });
      }
    }

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          id: idUsuario,
          email
        })
      }
    );

    if (!authResp.ok) {
      const erro = await authResp.text();

      return res.status(authResp.status).send(erro);
    }

    const authAtualizado = await authResp.json();

    const clienteResp = await fetch(
      `http://localhost:8082/clientes/${cpf}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          nome,
          salario,
          endereco,
          cep: CEP,
          cidade,
          estado
        })
      }
    );

    if (!clienteResp.ok) {
      const erro = await clienteResp.text();

      return res.status(clienteResp.status).send(erro);
    }

    const clienteAtualizado = await clienteResp.json();

    const contaResp = await fetch(
      `http://localhost:9090/contas/${numeroConta}/saldo`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          valor: salario
        })
      }
    );

    if (!contaResp.ok) {
      const erro = await contaResp.json();

      return res.status(contaResp.status).json({
        message: 'Erro ao atualizar conta',
        detalhe: erro
      });
    }

    await contaResp.json();

    console.log(clienteAtualizado);

    return res.status(200).json({
      cpf: clienteAtualizado.cpf,
      nome: clienteAtualizado.nome,
      email: authAtualizado.email,
      salario: clienteAtualizado.salario,
      endereco: clienteAtualizado.endereco,
      cep: clienteAtualizado.cep,
      cidade: clienteAtualizado.cidade,
      estado: clienteAtualizado.estado
    });

  } catch (err) {
    console.error(err);

    return res.status(500).json({
      erro: 'Erro interno no gateway',
      detalhe: err.message
    });
  }
});
app.post('/clientes/:cpf/aprovar', validacaoToken, async (req, res) => {
  try{
    if (req.usuario.tipo !== "GERENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    const { cpf } = req.params;

    const clienteResp = await fetch(
      `http://localhost:8082/clientes/${cpf}/aprovar`,
      {
        method:"POST"
      }
    );

    if(!clienteResp.ok){
      const erro = await clienteResp.json();

      return res.status(clienteResp.status).json(erro);
    }

    const cliente = await clienteResp.json();

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios/${cliente.idUsuario}/aprovar`,
      {
        method:"POST"
      }
    );

    if(!authResp.ok){
      const erro = await authResp.json();

      return res.status(authResp.status).json(erro);
    }

    //Aqui precisamos de um endpoint que vai criar a conta

    const contaResp = await fetch(
      `http://localhost:9090/contas`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          //Essas são as entradas que esse endpoint vai receber pra poder criar a conta (R10)*
          clienteCpf: cliente.cpf,
          gerenteCpf: cliente.cpfGerente,
          salario: cliente.salario
        })
      }
    );

    if(!contaResp.ok){
      const erro = await contaResp.json();

      return res.status(contaResp.status).json(erro);
    }

    return res.json({
      message: contaResp
    });

  } catch(err){
    return res.status(500).json({
      message: "Erro na API Composition",
      error: err.message
    });
  }
});
app.post('/clientes/:cpf/rejeitar', validacaoToken, async (req, res) => {
  try{
    if (req.usuario.tipo !== "GERENTE") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    const { cpf } = req.params;
    const { motivo } = req.body;

    const clienteResp = await fetch(
      `http://localhost:8082/clientes/${cpf}/rejeitar`,
      {
        method:"POST",
        headers:{
          "Content-Type":"application/json"
        },
        body: JSON.stringify({ motivo })
      }
    );

    if(!clienteResp.ok){
      const erro = await clienteResp.json();
      return res.status(clienteResp.status).json(erro);
    }

    const cliente = await clienteResp.json();

    const authResp = await fetch(
      `http://localhost:5000/auth/usuarios/${cliente.idUsuario}/rejeitar`,
      {
        method:"POST",
        headers:{
          "Content-Type":"application/json"
        },
        body: JSON.stringify({ motivo })
      }
    );

    if(!authResp.ok){
      const erro = await authResp.json();
      return res.status(authResp.status).json(erro);
    }

    return res.json({
      message:`Cliente rejeitado por motivo de: ${motivo}`
    });

  } catch(err){
    return res.status(500).json({
      message:"Erro na API Composition",
      error:err.message
    });
  }
});

//Gerente
//Listar gerentes
app.get("/gerentes", validacaoToken, async (req, res) => {
  try {
    if (req.usuario.tipo !== "ADMINISTRADOR") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

    const { filtro } = req.query;

    const usuariosFuncionariosResp = await fetch("http://localhost:5000/auth/usuarios/funcionarios");

    if (!usuariosFuncionariosResp.ok) {
      const erro = await usuariosFuncionariosResp.text();

      return res.status(usuariosFuncionariosResp.status).json({ 
        message: erro 
      });
    }

    const usuariosFuncionarios = await usuariosFuncionariosResp.json();

    const gerentesResp = await fetch("http://localhost:8083/gerentes");

    if (!gerentesResp.ok) {
      const erro = await gerentesResp.text();

      return res.status(gerentesResp.status).json({
        message: erro 
      });
    }

    const gerentes = await gerentesResp.json();

    const gerentesFiltrados = gerentes.filter((gerente) => {
      const usuario = usuariosFuncionarios.find(
        (u) => String(u.id) === String(gerente.idUsuario)
      );

      return usuario?.tipo === "GERENTE";
    });

    const gerentesResultado = gerentesFiltrados.map((gerente) => {
      const usuarioFuncionario = usuariosFuncionarios.find(
        (usuario) => String(usuario.id) === String(gerente.idUsuario)
      );

      return {
        nome: gerente.nome,
        cpf: gerente.cpf,
        email: usuarioFuncionario?.email,
        tipo: usuarioFuncionario?.tipo,
      };
    });

    if (filtro === "dashboard") {
      const clientesResp = await fetch("http://localhost:8082/clientes");

      if (!clientesResp.ok) {
        const erro = await clientesResp.text();

        return res.status(clientesResp.status).json({ 
          message: erro 
        });
      }

      const clientes = await clientesResp.json();

      const contasResp = await fetch("http://localhost:8081/contas");

      if (!contasResp.ok) {
        const erro = await contasResp.text();

        return res.status(contasResp.status).json({ 
          message: erro 
        });
      }

      const contas = await contasResp.json();

      const dashboard = gerentes.map((gerente) => {
        const usuarioFuncionario = usuariosFuncionarios.find(
          (usuario) => String(usuario.id) === String(gerente.idUsuario)
        );

       const contasDoGerente = contas.filter(
          (conta) => String(conta.gerenteCpf) === String(gerente.cpf)
        );

        const clientesDashboard = contasDoGerente.map((conta) => {
          return {
            cliente: conta.clienteCpf,
            numero: conta.numero,
            saldo: conta.saldo,
            limite: conta.limite,
            gerente: conta.gerenteCpf,
            criacao: conta.data,
          };
        });

        const saldoPositivo = contasDoGerente
          .filter((c) => c.saldo > 0)
          .reduce((s, c) => s + c.saldo, 0);

        const saldoNegativo = contasDoGerente
          .filter((c) => c.saldo < 0)
          .reduce((s, c) => s + c.saldo, 0);

        return {
          gerente: {
            cpf: gerente.cpf,
            nome: gerente.nome,
            email: usuarioFuncionario?.email,
            tipo: usuarioFuncionario?.tipo,
          },
          clientes: clientesDashboard,
          saldo_positivo: saldoPositivo,
          saldo_negativo: saldoNegativo,
        };
      });

      return res.json(dashboard);
    }

    return res.json(gerentesResultado);
  } catch (err) {
    return res.status(500).json({
      message: "Erro na API Composition",
      error: err.message,
    });
  }
});

//Todas as alterações realizadas que usam SAGA precisam esperar a resposta, por isso usa-se o get pra todas elas.
async function verificarStatusSaga(tipo, id){
  while(true){
    await new Promise(r =>
      setTimeout(r,500)
    );

    const resposta = await fetch(`http://localhost:8084/${tipo}/status/${id}`);

    const body = await resposta.json();

    if(resposta.status === 202){
      continue;
    }

    return {
      statusCode: resposta.status,
      body: body
    };
  }
}
app.post('/gerentes', validacaoToken, async(req,res)=>{
  try{
    const inicio = await fetch(
      "http://localhost:8084/gerentes",
      {
        method:"POST",
        headers:{
          "Content-Type":"application/json"
        },
        body:JSON.stringify(req.body)
      }
    );

    const bodyInicio = await inicio.json();

    const sagaId = bodyInicio.idSaga;

    const resultado = await verificarStatusSaga("gerentes", sagaId);

    return res
      .status(resultado.statusCode)
      .json(resultado.body);
  } catch(e){
    console.log(e);

    return res.status(500).json({
      erro: e.message,
      stack: e.stack
    });
  }
});
app.put('/gerentes/:cpf', validacaoToken, async (req, res) => {
  try {
    if (req.usuario.tipo !== "ADMINISTRADOR") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

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
          tipo: 'email',
          message: 'Email já cadastrado. Tente novamente.'
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
    if (req.usuario.tipo !== "ADMINISTRADOR") {
      return res.status(403).json({
        message: "Acesso negado"
      });
    }

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
      `http://localhost:9090/contas/redistribuir-gerente`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          gerenteCpf: cpf
        })
      }
    );

    if (!redistribuicaoResp.ok) {
      const erro = await redistribuicaoResp.text();

      return res
        .status(redistribuicaoResp.status)
        .send(erro);
    }

    const redistribuicaoData = await redistribuicaoResp.json();
    const novoGerente = redistribuicaoData.gerenteCpf;

    const clienteResp = await fetch(`http://localhost:8082/clientes/trocar-gerente`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        gerenteAntigo: cpf,
        gerenteNovo: novoGerente

      })
    });

    if (!clienteResp.ok) {
      const erro = await clienteResp.text();

      return res
        .status(clienteResp.status)
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
app.get('/contas/:numero/saldo', validacaoToken, contaQueryServiceProxy);
app.post('/contas/:numero/depositar', validacaoToken, contaCommandServiceProxy);
app.post('/contas/:numero/sacar', validacaoToken, contaCommandServiceProxy);
app.post('/contas/:numero/transferir', validacaoToken, contaCommandServiceProxy);
app.get('/contas/:numero/extrato', validacaoToken, contaQueryServiceProxy);

var server = http.createServer(app);

server.listen(3000, () => {
  console.log("API Gateway rodando na porta 3000");
});