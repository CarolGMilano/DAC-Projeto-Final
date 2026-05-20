package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msconta.model.dto.ComandoConta;
import br.net.dac.msconta.model.dto.ContaDTO;
import br.net.dac.msconta.model.dto.ContaRequestDTO;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.exception.ContaInativaException;
import br.net.dac.msconta.service.ContaCommandService;

    // 
@Component
public class ContaConsumidor {
    
    @Autowired
    private ContaCommandService contaCommandService;

    @Autowired
    private ContaProdutor contaProdutor;
    
    // MÉTODOS
        // 1. RECEBE COMANDO E DECIDE QUAL MÉTODO SERÁ UTILIZADO
    @RabbitListener(queues = "msconta.queue.commando")
    public void handleCommand(ComandoConta comando) {
        switch (comando.getTipo()) {
            case "CRIAR_CONTA":

                break;
            
            case "ROLLBACK_CRIAR_CONTA":
                break;

            case "ATUALIZAR_CONTA":
                break;
            
            case "ROLLBACK_ATUALIZAR_CONTA":
                break;
            
            case "DELETAR_CONTA":
                break;            
            
            case "ROLLBACK_DELETAR_CONTA":
                break;
            
            default:
                System.out.println("Tipo desconhecido: " + comando.getTipo());
        }
    }

    public void criar (ContaDTO contaDTO) {
        try {
            // Mapeamento de ContaDTO para ContaRequestDTO
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setAtivo(contaDTO.isAtivo());
            requestDTO.setIdGerente(contaDTO.getIdGerente());
            requestDTO.setIdCliente(contaDTO.getIdCliente());
            requestDTO.setSaldo(contaDTO.getSaldo());
            requestDTO.setLimite(contaDTO.getLimite());
            
            ContaResponseDTO contaAdicionada = contaCommandService.criarConta(requestDTO);

            contaProdutor.contaCriacaoSucesso(
                new ContaCreatedEvent(
                    contaAdicionada.getNumeroConta(),
                    contaAdicionada.getSaldo(),
                    contaAdicionada.getLimite()
                )
            );
        } catch (Exception e) {
            // PENDENTE: ADICIONAR TRATAMENTO DE EXCEÇÕES
        }
    }

}
