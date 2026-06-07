package br.net.dac.saga.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.saga.model.contexto.SagaAutocadastroContexto;
import br.net.dac.saga.model.dto.AutocadastroDTO;
import br.net.dac.saga.model.dto.ClienteInsercaoDTO;
import br.net.dac.saga.model.dto.UsuarioCriacaoDTO;
import br.net.dac.saga.model.dto.UsuarioDesativacaoDTO;
import br.net.dac.saga.model.enums.TipoUsuarioEnum;
import br.net.dac.saga.model.event.AuthCriacaoFalhaEvent;
import br.net.dac.saga.model.event.AuthSucessoEvent;
import br.net.dac.saga.model.event.ClienteInsercaoFalhaEvent;
import br.net.dac.saga.model.event.Evento;
import br.net.dac.saga.rabbitMQ.RabbitMQConfig;
import br.net.dac.saga.rabbitMQ.SagaProdutor;
import tools.jackson.databind.ObjectMapper;

@Service
public class AutocadastroSagaService {
  @Autowired
  private SagaProdutor sagaProdutor;

  private SagaAutocadastroContexto contexto;

  private Map<String, SagaAutocadastroContexto> sagas = new ConcurrentHashMap<>();

  public SagaAutocadastroContexto getContexto() {
    return contexto;
  }

  public SagaAutocadastroContexto buscar(String idSaga) {
    return sagas.get(idSaga);
  }

  public SagaAutocadastroContexto iniciar(AutocadastroDTO dto) {
    System.out.println("Iniciando saga [Autocadastro]...");
    String idSaga = UUID.randomUUID().toString();
    
    contexto = new SagaAutocadastroContexto();

    System.out.println(dto.toString());

    contexto.setIdSaga(idSaga);
    contexto.setStatus("PROCESSANDO");
    contexto.setCodigoErro(null);
    contexto.setErro(null);

    sagas.put(idSaga, contexto);
    
    contexto.setCpf(dto.getCpf());
    contexto.setCpfGerente(dto.getCpfGerente());
    contexto.setEmail(dto.getEmail());
    contexto.setNome(dto.getNome());
    contexto.setTelefone(dto.getTelefone());
    contexto.setSalario(dto.getSalario());
    contexto.setEndereco(dto.getEndereco());
    contexto.setCep(dto.getCep());
    contexto.setCidade(dto.getCidade());
    contexto.setEstado(dto.getEstado());
    contexto.setTipo(TipoUsuarioEnum.CLIENTE.name());
    contexto.setAtivo("PENDENTE");

    UsuarioCriacaoDTO auth = new UsuarioCriacaoDTO();

    auth.setEmail(contexto.getEmail());
    auth.setTipo(contexto.getTipo());

    System.out.println("Criando auth: " + auth.toString());

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "CRIAR_USUARIO",
      auth
    );

    return contexto;
  }
  
  public void criarCliente(Evento evento) {
    ObjectMapper mapper = new ObjectMapper();
    
    AuthSucessoEvent resposta =
    mapper.convertValue(
      evento.getPayload(),
      AuthSucessoEvent.class
    );
    
    contexto.setIdUsuario(resposta.getId());
    
    ClienteInsercaoDTO cliente = new ClienteInsercaoDTO();
    
    cliente.setIdUsuario(contexto.getIdUsuario());
    cliente.setCpf(contexto.getCpf());
    cliente.setNome(contexto.getNome());
    cliente.setTelefone(contexto.getTelefone());
    cliente.setSalario(contexto.getSalario());
    cliente.setEndereco(contexto.getEndereco());
    cliente.setCep(contexto.getCep());
    cliente.setCidade(contexto.getCidade());
    cliente.setEstado(contexto.getEstado());
    cliente.setCpfGerente(contexto.getCpfGerente());
    
    System.out.println("Criando cliente: " + cliente.toString());

    sagaProdutor.enviar(
      RabbitMQConfig.CLIENTE_QUEUE_COMANDO,
      "CRIAR_CLIENTE",
      cliente
    );
  }
  
  //Em caso de falha
  public void authFalhou(Evento evento) {
    ObjectMapper mapper = new ObjectMapper();

    AuthCriacaoFalhaEvent erro = mapper.convertValue(
      evento.getPayload(), 
      AuthCriacaoFalhaEvent.class
    );

    contexto.setStatus("FALHA");
    contexto.setCodigoErro(erro.getCodigo());
    contexto.setErro(erro.getMensagem());

    System.out.println("Erro auth: " + evento.toString());
  }

  public void clienteFalhou(Evento evento) {
    System.out.println("Cliente falhou: rollback no Auth " + evento.toString());

    ObjectMapper mapper = new ObjectMapper();

    ClienteInsercaoFalhaEvent erro = mapper.convertValue(
      evento.getPayload(), 
      ClienteInsercaoFalhaEvent.class
    );

    contexto.setStatus("FALHA");
    contexto.setCodigoErro(erro.getCodigo());
    contexto.setErro(erro.getMensagem());

    UsuarioDesativacaoDTO rollback = new UsuarioDesativacaoDTO();
    rollback.setId(contexto.getIdUsuario());

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "ROLLBACK_CRIAR_CLIENTE",
      rollback
    );

    contexto = null;
  }

  public void finalizar(Evento evento) {
    contexto.setStatus("SUCESSO");
    contexto.setErro(null);

    System.out.println("Saga [Autocadastro] finalizada com sucesso. Cliente criado: " + evento.toString());

    contexto = null;
  }
}