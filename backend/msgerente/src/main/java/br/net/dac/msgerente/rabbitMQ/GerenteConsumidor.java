package br.net.dac.msgerente.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msgerente.model.dto.GerenteDTO;
import br.net.dac.msgerente.model.event.GerenteCriacaoFalhaEvent;
import br.net.dac.msgerente.model.event.GerenteCriacaoSucessoEvent;
import br.net.dac.msgerente.service.GerenteService;

@Component
public class GerenteConsumidor {
  @Autowired
  private GerenteService gerenteService;

  @Autowired
  private GerenteProdutor gerenteProdutor;

  @RabbitListener(queues = "msgerente.criacao.queue")
  public void processarCriacao(GerenteDTO gerenteDTO) {
    try {
      GerenteDTO gerenteAdicionado = gerenteService.inserirGerente(gerenteDTO);

      gerenteProdutor.criacaoSucesso(
        new GerenteCriacaoSucessoEvent(
          gerenteAdicionado.getId(),
          gerenteAdicionado.getNome()
        )
      );

    } catch (Exception e) {
      gerenteProdutor.criacaoFalha(
        new GerenteCriacaoFalhaEvent("ERRO", e.getMessage())
      );
    }
  }
}
