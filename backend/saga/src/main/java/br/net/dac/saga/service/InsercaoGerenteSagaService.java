package br.net.dac.saga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.saga.model.dto.GerenteDTO;
import br.net.dac.saga.model.event.Evento;
import br.net.dac.saga.rabbitMQ.RabbitMQConfig;
import br.net.dac.saga.rabbitMQ.SagaProdutor;

@Service
public class InsercaoGerenteSagaService {
  @Autowired
  private SagaProdutor sagaProdutor;

  public void iniciar(GerenteDTO dto) {
    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "CRIAR_USUARIO",
      dto
    );
  }

  public void criarGerente(Evento evento) {
    sagaProdutor.enviar(
      RabbitMQConfig.GERENTE_QUEUE_COMANDO,
      "CRIAR_GERENTE",
      evento.getPayload()
    );
  }
  
  public void vincularConta(Evento evento) {
    sagaProdutor.enviar(
      RabbitMQConfig.CONTA_QUEUE_COMANDO,
      "VINCULAR_CONTA",
      evento.getPayload()
    );
  }
  
  //Em caso de falha
  public void authFalhou(Evento evento) {
    System.out.println("Erro auth: " + evento.getPayload());
  }

  public void gerenteFalhou(Evento evento) {
    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "ROLLBACK_INSERIR_GERENTE",
      evento.getPayload()
    );
  }

  public void contaFalhou(Evento evento) {
    sagaProdutor.enviar(
      RabbitMQConfig.GERENTE_QUEUE_COMANDO,
      "ROLLBACK_INSERIR_GERENTE",
      evento.getPayload()
    );

    sagaProdutor.enviar(
      RabbitMQConfig.AUTH_QUEUE_COMANDO,
      "ROLLBACK_INSERIR_GERENTE",
      evento.getPayload()
    );
  }

  public void finalizar(Evento evento) {
    System.out.println("Saga concluída com sucesso");
  }
}