package br.net.dac.saga.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.saga.model.event.Evento;

@Component
public class SagaProdutor {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void enviar(String fila, String tipo, Object payload) {
    Evento evento = new Evento();

    evento.setTipo(tipo);
    evento.setPayload(payload);

    rabbitTemplate.convertAndSend(
      fila,
      evento
    );
  }
}
