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

  //Nome da exchange (mesma que foi configurado)
  private static final String EXCHANGE = "bantads.exchange";

  //Routing keys de SAÍDA (eventos que esse MS vai publicar)
  private static final String GERENTE_CRIACAO_SUCESSO = "MSGERENTE.CRIACAO.SUCESSO";
  private static final String GERENTE_CRIACAO_FALHA = "MSGERENTE.CRIACAO.FALHA";

  //private static final String GERENTE_ALTERACAO_SUCESSO = "MSGERENTE.ALTERACAO.SUCESSO";
  //private static final String GERENTE_ALTERACAO_FALHA = "MSGERENTE.ALTERACAO.FALHA";
  
  //private static final String GERENTE_DELECAO_SUCESSO = "MSGERENTE.DELECAO.SUCESSO";
  //private static final String GERENTE_DELECAO_FALHA = "MSGERENTE.DELECAO.FALHA";

  //Evento de sucesso
  public void criacaoSucesso(GerenteCriacaoSucessoEvent evento) {
    System.out.println("Enviando GERENTE_CRIACAO_SUCESSO" + evento);
    rabbitTemplate.convertAndSend(EXCHANGE, GERENTE_CRIACAO_SUCESSO, evento);
  }

  //Evento de erro
  public void criacaoFalha(GerenteCriacaoFalhaEvent erro) {
    System.out.println("Enviando GERENTE_CRIACAO_FALHA -> " + erro.getMensagem());
    rabbitTemplate.convertAndSend(EXCHANGE, GERENTE_CRIACAO_FALHA, erro);
  }
}
