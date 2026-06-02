package br.net.dac.saga.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.saga.model.contexto.SagaInsercaoContexto;
import br.net.dac.saga.model.dto.GerenteCriacaoDTO;
import br.net.dac.saga.model.dto.GerenteCriacaoResumoDTO;
import br.net.dac.saga.model.dto.GerenteDTO;
import br.net.dac.saga.model.dto.GerenteRollbackDTO;
import br.net.dac.saga.model.dto.UsuarioCriacaoDTO;
import br.net.dac.saga.model.dto.UsuarioDesativacaoDTO;
import br.net.dac.saga.model.event.AuthCriacaoFalhaEvent;
import br.net.dac.saga.model.event.AuthSucessoEvent;
import br.net.dac.saga.model.event.Evento;
import br.net.dac.saga.model.event.GerenteCriacaoFalhaEvent;
import br.net.dac.saga.rabbitMQ.RabbitMQConfig;
import br.net.dac.saga.rabbitMQ.SagaProdutor;
import tools.jackson.databind.ObjectMapper;

@Service
public class InsercaoGerenteSagaService {
  @Autowired
  private SagaProdutor sagaProdutor;

  private SagaInsercaoContexto contexto;

  private Map<String, SagaInsercaoContexto> sagas = new ConcurrentHashMap<>();

  public SagaInsercaoContexto getContexto() {
    return contexto;
  }

  public SagaInsercaoContexto buscar(String idSaga) {
    return sagas.get(idSaga);
  }

  public SagaInsercaoContexto iniciar(GerenteDTO dto) {
    System.out.println("Iniciando saga [Inserção de Gerente]...");
    String idSaga = UUID.randomUUID().toString();
    
    contexto = new SagaInsercaoContexto();

    contexto.setIdSaga(idSaga);
    contexto.setStatus("PROCESSANDO");
    contexto.setCodigoErro(null);
    contexto.setErro(null);

    sagas.put(idSaga, contexto);
    
    contexto.setCpf(dto.getCpf());
    contexto.setNome(dto.getNome());
    contexto.setEmail(dto.getEmail());
    contexto.setSenha(dto.getSenha());
    contexto.setTipo(dto.getTipo());
    contexto.setAtivo(true);
    
    UsuarioCriacaoDTO auth = new UsuarioCriacaoDTO();

    auth.setEmail(dto.getEmail());
    auth.setSenha(dto.getSenha());
    auth.setTipo(dto.getTipo());

    System.out.println("Criando auth: " + auth.toString());

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "CRIAR_USUARIO",
      auth
    );

    return contexto;
  }

  public void criarGerente(Evento evento) {
    ObjectMapper mapper = new ObjectMapper();
    
    AuthSucessoEvent resposta =
    mapper.convertValue(
      evento.getPayload(),
      AuthSucessoEvent.class
    );
    
    contexto.setIdUsuario(resposta.getId());
    
    GerenteCriacaoDTO gerente = new GerenteCriacaoDTO();
    
    gerente.setCpf(contexto.getCpf());
    gerente.setNome(contexto.getNome());
    gerente.setIdUsuario(contexto.getIdUsuario());
    
    System.out.println("Criando gerente: " + gerente.toString());
    sagaProdutor.enviar(
      RabbitMQConfig.GERENTE_QUEUE_COMANDO,
      "CRIAR_GERENTE",
      gerente
    );
  }
  
  public void vincularConta(Evento evento) {
    ObjectMapper mapper = new ObjectMapper();

    GerenteCriacaoDTO resposta =
      mapper.convertValue(
        evento.getPayload(),
        GerenteCriacaoDTO.class
      );

    contexto.setId(resposta.getId());

    GerenteCriacaoResumoDTO resumo = new GerenteCriacaoResumoDTO();

    resumo.setId(resposta.getId());
    resumo.setCpf(contexto.getCpf());

    System.out.println("Vinculando conta ao gerente: " + resumo.toString());

    if(contexto.getTipo().equals("ADMIN")){
      //Pula a vinculação, porque não é gerente.
      sagaProdutor.enviar(
        RabbitMQConfig.CONTA_QUEUE_SUCESSO,
        "VINCULAR_GERENTE_SUCESSO",
        resumo
      );
    } else {
      sagaProdutor.enviar(
        RabbitMQConfig.CONTA_QUEUE_COMANDO,
        "VINCULAR_CONTA",
        resumo
      );
    }
  }
  
  //Em caso de falha
  public void authFalhou(Evento evento) {
    ObjectMapper mapper = new ObjectMapper();

    AuthCriacaoFalhaEvent erro = mapper.convertValue(evento.getPayload(), AuthCriacaoFalhaEvent.class);

    contexto.setStatus("FALHA");
    contexto.setCodigoErro(erro.getCodigo());
    contexto.setErro(erro.getMensagem());

    System.out.println("Erro auth: " + evento.toString());
  }

  public void gerenteFalhou(Evento evento) {
    System.out.println("Gerente falhou: rollback no Auth " + evento.toString());

    ObjectMapper mapper = new ObjectMapper();

    GerenteCriacaoFalhaEvent erro = mapper.convertValue(evento.getPayload(), GerenteCriacaoFalhaEvent.class);

    contexto.setStatus("FALHA");
    contexto.setCodigoErro(erro.getCodigo());
    contexto.setErro(erro.getMensagem());

    UsuarioDesativacaoDTO rollback = new UsuarioDesativacaoDTO();
    rollback.setId(contexto.getIdUsuario());

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "ROLLBACK_CRIAR_GERENTE",
      rollback
    );

    contexto = null;
  }

  public void contaFalhou(Evento evento) {
    System.out.println("Conta falhou: rollback no Auth e Gerente " + evento.toString());

    contexto.setStatus("FALHA");
    contexto.setErro(
        String.valueOf(evento.getPayload())
    );

    UsuarioDesativacaoDTO rollbackAuth = new UsuarioDesativacaoDTO();
    rollbackAuth.setId(contexto.getIdUsuario());

    GerenteRollbackDTO rollbackGerente = new GerenteRollbackDTO();

    rollbackGerente.setId(contexto.getId());
    rollbackGerente.setIdUsuario(contexto.getIdUsuario());
    rollbackGerente.setCpf(contexto.getCpf());
    rollbackGerente.setNome(contexto.getNome());
    rollbackGerente.setAtivo(contexto.getAtivo());

    System.out.println("Realizando rollback do auth: " + rollbackAuth.toString());
    System.out.println("Realizando rollback do gerente: " + rollbackGerente.toString());

    sagaProdutor.enviar(
      RabbitMQConfig.GERENTE_QUEUE_COMANDO,
      "ROLLBACK_CRIAR_GERENTE",
      rollbackGerente
    );

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "ROLLBACK_CRIAR_GERENTE",
      rollbackAuth
    );

    contexto = null;
  }

  public void finalizar(Evento evento) {
    contexto.setStatus("SUCESSO");
    contexto.setErro(null);

    System.out.println("Saga [Inserção de gerente] finalizada com sucesso. Gerente criado: " + evento.toString());

    contexto = null;
  }
}