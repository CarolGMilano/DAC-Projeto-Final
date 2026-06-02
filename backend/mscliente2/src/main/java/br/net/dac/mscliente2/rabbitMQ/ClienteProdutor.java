package br.net.dac.mscliente2.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.mscliente2.model.event.ClienteAlteracaoFalhaEvent;
import br.net.dac.mscliente2.model.event.ClienteSucessoEvent;
import br.net.dac.mscliente2.model.event.Evento;

@Component
public class ClienteProdutor {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public static final String CRIACAO_SUCESSO = "mscliente.queue.sucesso";
  public static final String CRIACAO_FALHA = "mscliente.queue.falha";

  //Criar objeto genérico
  public void criacaoSucesso(ClienteSucessoEvent payload) {
    Evento evento = new Evento();

    evento.setTipo("ALTERAR_GERENTE_SUCESSO");
    evento.setPayload(payload);

    rabbitTemplate.convertAndSend(CRIACAO_SUCESSO, evento);
  }

  public void criacaoFalha(ClienteAlteracaoFalhaEvent erro) {
    Evento evento = new Evento();

    evento.setTipo("ALTERAR_GERENTE_FALHA");
    evento.setPayload(erro);

    rabbitTemplate.convertAndSend(CRIACAO_FALHA, evento);
  }
}
