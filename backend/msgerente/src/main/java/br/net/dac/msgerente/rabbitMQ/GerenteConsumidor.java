package br.net.dac.msgerente.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msgerente.model.dto.ComandoGerente;
import br.net.dac.msgerente.model.dto.GerenteDTO;
import br.net.dac.msgerente.model.enums.CodigoErroEnum;
import br.net.dac.msgerente.model.event.GerenteCriacaoFalhaEvent;
import br.net.dac.msgerente.model.event.GerenteCriacaoSucessoEvent;
import br.net.dac.msgerente.model.exception.CPFDuplicadoException;
import br.net.dac.msgerente.model.exception.UsuarioDuplicadoException;
import br.net.dac.msgerente.service.GerenteService;

@Component
public class GerenteConsumidor {
  @Autowired
  private GerenteService gerenteService;

  @Autowired
  private GerenteProdutor gerenteProdutor;

  @RabbitListener(queues = "msgerente.queue.comando")
  public void processar(ComandoGerente comando) {
    switch (comando.getTipo()) {
      case "CRIAR_GERENTE":
        criar(comando.getPayload());
      break;

      case "ROLLBACK_CRIAR_GERENTE":
        rollback(comando.getPayload());
      break;

      default:
        System.out.println("Tipo desconhecido: " + comando.getTipo());
    }
  }

  public void criar(GerenteDTO gerenteDTO) {
    try {
      GerenteDTO gerenteAdicionado = gerenteService.inserirGerente(gerenteDTO);

      gerenteProdutor.criacaoSucesso(
        new GerenteCriacaoSucessoEvent(
          gerenteAdicionado.getId(),
          gerenteAdicionado.getNome()
        )
      );

    } catch (CPFDuplicadoException e) {
      gerenteProdutor.criacaoFalha(
        new GerenteCriacaoFalhaEvent(
          CodigoErroEnum.CPF_DUPLICADO.name(),
          e.getMessage()
        )
      );
    } catch (UsuarioDuplicadoException e) {
      gerenteProdutor.criacaoFalha(
        new GerenteCriacaoFalhaEvent(
          CodigoErroEnum.USUARIO_DUPLICADO.name(),
          e.getMessage()
        )
      );
    }catch (Exception e) {
      gerenteProdutor.criacaoFalha(
        new GerenteCriacaoFalhaEvent(
          CodigoErroEnum.ERRO_INTERNO.name(),
          e.getMessage()
        )
      );
    }
  }

  public void rollback(GerenteDTO gerenteDTO) {
    try {
      gerenteService.rollback(gerenteDTO);
    } catch (Exception e) {
      System.out.println("Erro rollback gerente: " + e.getMessage());
    }
  }
}