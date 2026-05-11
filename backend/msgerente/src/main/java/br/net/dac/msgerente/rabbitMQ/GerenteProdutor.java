package br.net.dac.msgerente.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msgerente.model.event.GerenteCriacaoFalhaEvent;
import br.net.dac.msgerente.model.event.GerenteCriacaoSucessoEvent;

@Component
public class GerenteProdutor {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public static final String CRIACAO_SUCESSO = "msgerente.criacao.sucesso";
  public static final String CRIACAO_FALHA = "msgerente.criacao.falha";

  //Criar objeto genérico
  public void criacaoSucesso(GerenteCriacaoSucessoEvent evento) {
    rabbitTemplate.convertAndSend(CRIACAO_SUCESSO, evento);
  }

  public void criacaoFalha(GerenteCriacaoFalhaEvent erro) {
    rabbitTemplate.convertAndSend(CRIACAO_FALHA, erro);
  }
}