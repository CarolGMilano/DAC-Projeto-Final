package br.net.dac.saga.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.saga.model.event.Evento;
import br.net.dac.saga.service.AutocadastroSagaService;
import br.net.dac.saga.service.InsercaoGerenteSagaService;

@Component
public class SagaConsumidor {
  @Autowired
  private InsercaoGerenteSagaService insercaoGerenteSagaService;

  @Autowired
  private AutocadastroSagaService autocadastroSagaService;

  //MSAUTH
  @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_SUCESSO)
  public void authSucesso(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_USUARIO_SUCESSO":
        System.out.println("CRIAR_USUARIO_SUCESSO:" + evento);

        insercaoGerenteSagaService.criarGerente(evento);
      break;

      case "CRIAR_USUARIO_CLIENTE_SUCESSO":
        System.out.println("CRIAR_USUARIO_CLIENTE_SUCESSO:" + evento);

        autocadastroSagaService.criarCliente(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_FALHA)
  public void authFalha(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_USUARIO_FALHA":
        System.out.println("CRIAR_USUARIO_FALHA:" + evento);

        insercaoGerenteSagaService.authFalhou(evento);
      break;

      case "CRIAR_USUARIO_CLIENTE_FALHA":
        System.out.println("CRIAR_USUARIO_CLIENTE_FALHA:" + evento);

        autocadastroSagaService.authFalhou(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  //MSCLIENTE
  @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE_SUCESSO)
  public void clienteSucesso(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_CLIENTE_SUCESSO":
        System.out.println("CRIAR_CLIENTE_SUCESSO:" + evento);

        autocadastroSagaService.finalizar(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE_FALHA)
  public void clienteFalha(Evento evento) {
    switch(evento.getTipo()) {
      case "CRIAR_CLIENTE_FALHA":
        System.out.println("CRIAR_CLIENTE_FALHA:" + evento);

        autocadastroSagaService.clienteFalhou(evento);
      break;

      default:
        System.out.println("Tipo desconhecido: " + evento.getTipo());
    }
  }

  //MSGERENTE
  @RabbitListener(queues = RabbitMQConfig.GERENTE_QUEUE_SUCESSO)
  public void gerenteSucesso(Evento evento) {

    System.out.println("CRIAR_GERENTE_SUCESSO:" + evento);

    switch(evento.getTipo()) {
      case "CRIAR_GERENTE_SUCESSO":
        System.out.println("CRIAR_GERENTE_SUCESSO:" + evento);
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
    System.out.println("VINCULAR_GERENTE_SUCESSO:" + evento);

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
