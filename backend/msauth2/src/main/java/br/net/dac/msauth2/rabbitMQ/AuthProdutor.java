package br.net.dac.msauth2.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msauth2.model.event.AuthCriacaoFalhaEvent;
import br.net.dac.msauth2.model.event.AuthSucessoEvent;
import br.net.dac.msauth2.model.event.Evento;

@Component
public class AuthProdutor {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public static final String CRIACAO_SUCESSO = "msauth.queue.sucesso";
  public static final String CRIACAO_FALHA = "msauth.queue.falha";

  //Criar objeto genérico
  public void criacaoSucesso(AuthSucessoEvent payload) {
    Evento evento = new Evento();

    evento.setTipo("CRIAR_USUARIO_SUCESSO");
    evento.setPayload(payload);

    rabbitTemplate.convertAndSend(CRIACAO_SUCESSO, evento);
  }

  public void criacaoFalha(AuthCriacaoFalhaEvent erro) {
    Evento evento = new Evento();

    evento.setTipo("CRIAR_USUARIO_FALHA");
    evento.setPayload(erro);

    rabbitTemplate.convertAndSend(CRIACAO_FALHA, evento);
  }
}
