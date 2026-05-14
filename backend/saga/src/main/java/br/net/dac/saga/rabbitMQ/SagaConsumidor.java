package br.net.dac.saga.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.saga.model.event.Evento;
import br.net.dac.saga.service.InsercaoGerenteSagaService;

@Component
public class SagaConsumidor {
  @Autowired
  private InsercaoGerenteSagaService insercaoGerenteSagaService;

  //MSAUTH
  @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_SUCESSO)
  public void authSucesso(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_USUARIO_SUCESSO":
        insercaoGerenteSagaService.criarGerente(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_FALHA)
  public void authFalha(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_USUARIO_FALHA":
        insercaoGerenteSagaService.authFalhou(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  //MSCLIENTE
  @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE_SUCESSO)
  public void clienteSucesso(Evento evento) {
    switch(evento.getTipo()) {
      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE_FALHA)
  public void clienteFalha(Evento evento) {
    switch(evento.getTipo()) {
      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  //MSGERENTE
  @RabbitListener(queues = RabbitMQConfig.GERENTE_QUEUE_SUCESSO)
  public void gerenteSucesso(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_GERENTE_SUCESSO":
        insercaoGerenteSagaService.vincularConta(evento);
      break;
    }
  }

  @RabbitListener(queues = RabbitMQConfig.GERENTE_QUEUE_FALHA)
  public void gerenteFalha(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_GERENTE_FALHA":
        insercaoGerenteSagaService.gerenteFalhou(evento);
      break;
    }
  }

  //MSCONTA
  @RabbitListener(queues = RabbitMQConfig.CONTA_QUEUE_SUCESSO)
  public void contaSucesso(Evento evento) {
    switch(evento.getTipo()) {
      case "VINCULAR_GERENTE_SUCESSO":
        insercaoGerenteSagaService.finalizar(evento);
      break;
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CONTA_QUEUE_FALHA)
  public void contaFalha(Evento evento) {
    switch(evento.getTipo()) {
      case "VINCULAR_GERENTE_FALHA":
        insercaoGerenteSagaService.contaFalhou(evento);
      break;
    }
  }
}
