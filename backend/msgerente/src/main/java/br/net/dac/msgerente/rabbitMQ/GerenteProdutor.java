package br.net.dac.msgerente.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msgerente.model.event.Evento;
import br.net.dac.msgerente.model.event.GerenteCriacaoFalhaEvent;
import br.net.dac.msgerente.model.event.GerenteCriacaoSucessoEvent;

@Component
public class GerenteProdutor {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public static final String CRIACAO_SUCESSO = "msgerente.queue.sucesso";
  public static final String CRIACAO_FALHA = "msgerente.queue.falha";

  public void criacaoSucesso(GerenteCriacaoSucessoEvent payload) {
    Evento evento = new Evento();

    evento.setTipo("CRIAR_GERENTE_SUCESSO");
    evento.setPayload(payload);

    rabbitTemplate.convertAndSend(CRIACAO_SUCESSO, evento);
  }

  public void criacaoFalha(GerenteCriacaoFalhaEvent erro) {
    Evento evento = new Evento();

    evento.setTipo("CRIAR_GERENTE_FALHA");
    evento.setPayload(erro);

    rabbitTemplate.convertAndSend(CRIACAO_FALHA, evento);
  }
}