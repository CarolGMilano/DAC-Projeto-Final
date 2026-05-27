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

    const email = payload.sub;

    const authResp = await fetch(`http://localhost:5000/auth/usuarios/email/${email}`);

    if (!authResp.ok) {
      const erro = await authResp.json();

      return res.status(authResp.status).json(erro);
    }

    const usuario = await authResp.json();

    let dados;

    if (usuario.tipo === "GERENTE" || usuario.tipo === "ADMIN") {
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

      console.log("RESULTADO:", resultado);

      return res.json(resultado);
    }

    if(filtro==="adm_relatorio_clientes"){
      const contasResp = await fetch("http://localhost:8081/contas");
      const contas = await contasResp.json();

      const resultado = clientes.map(cliente=>{
        const usuarioCliente = usuariosC.find(usuario =>
          String(usuario.id) === String(cliente.idUsuario)
        );

        const conta = contas.find(conta =>
          String(conta.cpfCliente) === String(cliente.cpf)
        );

        const gerente = gerentes.find(gerente =>
          String(gerente.cpf) === String(conta?.cpfGerente)
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
      const usuario = usuariosC.find(usuario =>
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

    /*
    //Essa rota precisa existir no MSContas e vai devolver apenas o CPF do gerente com menos contas atreladas
    const contaResp = await fetch("http://localhost:8081/contas/disponivel");

    if (!contaResp.ok) {
      const erro = await contaResp.json();

      return res.status(contaResp.status).json(erro);
    }

    const { cpfGerente } = await gerenteResp.json();
    */
    const cpfGerente = '98574307084';
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
      const erro = await authResp.json();

      return res.status(authResp.status).json(erro);
    }

    const usuarioCriado = await authResp.json();

    const clienteResp = await fetch(
      "http://localhost:8082/clientes",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idUsuario: usuarioCriado.id,
          cpf,
          nome,
          telefone,
          salario,
          endereco,
          cep,
          cidade,
          estado,
          cpfGerente: cpfGerente
        })
      }
    );

    if (!clienteResp.ok) {
      await fetch(
        `http://localhost:5000/auth/usuarios/${usuarioCriado.id}`,
        { method: "DELETE" }
      );

      const erro = await clienteResp.json();

      return res.status(clienteResp.status).json(erro);
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

    const clienteResp = await fetch(`http://localhost:8082/clientes/${cpf}`);

    if (!clienteResp.ok) {
      const erro = await clienteResp.json();

      return res.status(clienteResp.status).json(erro);
    }

    const cliente = await clienteResp.json();

    const authResp = await fetch(`http://localhost:5000/auth/usuarios/${cliente.idUsuario}`);

    if (!authResp.ok) {
      const erro = await authResp.json();

      return res.status(authResp.status).json(erro);
    }

    const usuario = await authResp.json();

    /*
    const contaResp = await fetch(`http://localhost:8081/contas/cliente/${cpf}`);

    if (!contaResp.ok) {
      const erro = await contaResp.json();

      return res.status(contaResp.status).json(erro);
    }

    const conta = await contasResp.json();
    */

    let gerente = null;
    let usuarioGerente = null;

    if (cliente.cpfGerente) {
      const gerenteResp = await fetch(`http://localhost:8083/gerentes/${cliente.cpfGerente}`);

      if (!gerenteResp.ok) {
        const erro = await gerenteResp.json();

        return res.status(gerenteResp.status).json(erro);
      }

      gerente = await gerenteResp.json();

      const usuarioGerenteResp = await fetch(`http://localhost:5000/auth/usuarios/${gerente.idUsuario}`);

      if (!usuarioGerenteResp.ok) {
        const erro = await usuarioGerenteResp.json();

        return res.status(usuarioGerenteResp.status).json(erro);
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

      //conta: conta?.numero,
      //saldo: conta?.saldo,
      //limite: conta?.limite,

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
    const { cpf } = req.params;

    const {
      nome,
      email,
      salario,
      endereco,
      cep,
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
    const contaBusca = await fetch(
      `http://localhost:8081/contas/cliente/${cpf}`
    );

    if (!contaBusca.ok) {
      const erro = await contaBusca.json();

      return res.status(contaBusca.status).json(erro);
    }

    const conta = await contaBusca.json();
    const numeroConta = conta.numero;

    const clienteBusca = await fetch(
      `http://localhost:8083/clientes/${cpf}`
    );

    if (!clienteBusca.ok) {
      const erro = await clienteBusca.json();
      return res.status(clienteBusca.status).json(erro);
    }

    const cliente = await clienteBusca.json();
    const idUsuario = cliente.idUsuario;

    const usuarioExistenteResp = await fetch(
      `http://localhost:5000/auth/usuarios/email/${email}`
    );

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
      const erro = await authResp.json();
      return res.status(authResp.status).json(erro);
    }

    const authAtualizado = await authResp.json();

    const clienteResp = await fetch(
      `http://localhost:8083/clientes/${cpf}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          nome,
          salario,
          endereco,
          cep,
          cidade,
          estado
        })
      }
    );

    if (!clienteResp.ok) {
      const erro = await clienteResp.json();
      return res.status(clienteResp.status).json(erro);
    }

    const clienteAtualizado = await clienteResp.json();

    const contaResp = await fetch(
      `http://localhost:8081/contas/${numeroConta}/saldo`,
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
    console.log("CLIENTE:", cliente);
    console.log("ID: ", cliente.idUsuario);

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

    /*
      Aqui precisamos de um endpoint que vai criar a conta

      const contaResp = await fetch(
        `http://localhost:8081/contas`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            *Essas são as entradas que esse endpoint vai receber pra poder criar a conta (R10)*
            cpfCliente: cliente.cpf,
            cpfGerente: cliente.cpfGerente,
            salario: cliente.salario
          })
        );
    */

    return res.json({
      message: "Cliente aprovado com sucesso."
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