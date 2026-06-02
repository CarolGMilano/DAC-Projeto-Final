package br.net.dac.mscliente2.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.mscliente2.model.dto.ClienteAlterarGerenteDTO;
import br.net.dac.mscliente2.model.dto.ComandoCliente;
import br.net.dac.mscliente2.service.ClienteService;
import tools.jackson.databind.ObjectMapper;

@Component
public class ClienteConsumidor {
  @Autowired
  private ClienteService clienteService;

  private final ObjectMapper mapper = new ObjectMapper();

  @RabbitListener(queues = "mscliente.queue.comando")
  public void processar(ComandoCliente comando) {
    switch (comando.getTipo()) {
      case "ALTERAR_GERENTE":
        System.out.println("CHEGOU MENSAGEM CLIENTE: " + comando.getTipo());

        ClienteAlterarGerenteDTO dtoAlterar = mapper.convertValue(
          comando.getPayload(),
          ClienteAlterarGerenteDTO.class
        );

        System.out.println(dtoAlterar.getGerenteAntigo());
        System.out.println(dtoAlterar.getGerenteNovo());

        alterar(dtoAlterar);
      break;

      default:
        System.out.println("Tipo desconhecido: " + comando.getTipo());
    }
  }

  public void alterar(ClienteAlterarGerenteDTO dto) {
    try {
      clienteService.trocarGerenteInsercao(dto);

      System.out.println("Gerente do cliente atualizado.");

    } catch(Exception e) {
      System.out.println("Erro alterar gerente cliente: " + e.getMessage());
    }
  }

  public void rollback(ClienteAlterarGerenteDTO clienteAlteracaoDTO) {
    try {
      clienteService.rollback(clienteAlteracaoDTO);
    } catch (Exception e) {
      System.out.println("Erro rollback cliente: " + e.getMessage());
    }
  }
}