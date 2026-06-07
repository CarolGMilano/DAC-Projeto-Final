package br.net.dac.msauth2.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msauth2.model.dto.ComandoAuth;
import br.net.dac.msauth2.model.dto.RespostaDTO;
import br.net.dac.msauth2.model.dto.UsuarioCriacaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioDesativacaoDTO;
import br.net.dac.msauth2.model.enums.CodigoErroEnum;
import br.net.dac.msauth2.model.enums.TipoUsuarioEnum;
import br.net.dac.msauth2.model.event.AuthCriacaoFalhaEvent;
import br.net.dac.msauth2.model.event.AuthSucessoEvent;
import br.net.dac.msauth2.model.exception.EmailDuplicadoException;
import br.net.dac.msauth2.service.AuthService;
import tools.jackson.databind.ObjectMapper;

@Component
public class AuthConsumidor {
  @Autowired
  private AuthService authService;

  @Autowired
  private AuthProdutor authProdutor;

  private final ObjectMapper mapper = new ObjectMapper();

  @RabbitListener(queues = "msauth.queue.comando")
  public void processar(ComandoAuth comando) {
    switch (comando.getTipo()) {
      case "CRIAR_USUARIO":
        UsuarioCriacaoDTO dtoCriar = mapper.convertValue(
          comando.getPayload(),
          UsuarioCriacaoDTO.class
        );

        criar(dtoCriar);
      break;

      case "ROLLBACK_CRIAR_GERENTE":
        UsuarioDesativacaoDTO dtoDeletarGerente = mapper.convertValue(
          comando.getPayload(),
          UsuarioDesativacaoDTO.class
        );

        rollback(dtoDeletarGerente);
      break;

      case "ROLLBACK_CRIAR_CLIENTE":
        UsuarioDesativacaoDTO dtoDeletarCliente = mapper.convertValue(
          comando.getPayload(),
          UsuarioDesativacaoDTO.class
        );

        rollback(dtoDeletarCliente);
      break;

      default:
        System.out.println("Tipo desconhecido: " + comando.getTipo());
    }
  }

  public void criar(UsuarioCriacaoDTO usuarioCriacaoDTO) {
    try {
      RespostaDTO usuarioAdicionado = authService.criarUsuario(usuarioCriacaoDTO);

      if(usuarioAdicionado.getTipo().equals(TipoUsuarioEnum.GERENTE.name())){
        authProdutor.criacaoSucesso(
          "CRIAR_USUARIO_SUCESSO",
          new AuthSucessoEvent(
            usuarioAdicionado.getId(),
            usuarioAdicionado.getEmail(),
            usuarioAdicionado.getTipo()
          )
        );
      } else if(usuarioAdicionado.getTipo().equals(TipoUsuarioEnum.CLIENTE.name())){
        authProdutor.criacaoSucesso(
          "CRIAR_USUARIO_CLIENTE_SUCESSO",
          new AuthSucessoEvent(
            usuarioAdicionado.getId(),
            usuarioAdicionado.getEmail(),
            usuarioAdicionado.getTipo()
          )
        );
      }

    } catch (EmailDuplicadoException e) {
      if (usuarioCriacaoDTO.getTipo().equals(TipoUsuarioEnum.GERENTE.name())) {
        authProdutor.criacaoFalha(
          "CRIAR_USUARIO_FALHA",
          new AuthCriacaoFalhaEvent(
            CodigoErroEnum.EMAIL_DUPLICADO.name(),
            e.getMessage()
          )
        );
      } else if (usuarioCriacaoDTO.getTipo().equals(TipoUsuarioEnum.CLIENTE.name())) {
        authProdutor.criacaoFalha(
          "CRIAR_USUARIO_CLIENTE_FALHA",
          new AuthCriacaoFalhaEvent(
            CodigoErroEnum.EMAIL_DUPLICADO.name(),
            e.getMessage()
          )
        );
      }
    } catch (Exception e) {
      if (usuarioCriacaoDTO.getTipo().equals(TipoUsuarioEnum.GERENTE.name())) {
        authProdutor.criacaoFalha(
          "CRIAR_USUARIO_FALHA",
          new AuthCriacaoFalhaEvent(
            CodigoErroEnum.ERRO_INTERNO.name(),
            e.getMessage()
          )
        );
      } else if (usuarioCriacaoDTO.getTipo().equals(TipoUsuarioEnum.CLIENTE.name())) {
        authProdutor.criacaoFalha(
          "CRIAR_USUARIO_CLIENTE_FALHA",
          new AuthCriacaoFalhaEvent(
            CodigoErroEnum.ERRO_INTERNO.name(),
            e.getMessage()
          )
        );
      }
    }
  }

  public void rollback(UsuarioDesativacaoDTO usuarioDesativacaoDTO) {
    try {
      authService.rollback(usuarioDesativacaoDTO);
    } catch (Exception e) {
      System.out.println("Erro rollback gerente: " + e.getMessage());
    }
  }
}