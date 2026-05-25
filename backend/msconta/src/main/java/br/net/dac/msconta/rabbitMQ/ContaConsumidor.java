package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msconta.model.dto.ComandoConta;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.dto.ContaRequestDTO;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.event.ContaCreateFailedEvent;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.event.ContaDeleteFailedEvent;
import br.net.dac.msconta.model.event.ContaDeletedEvent;
import br.net.dac.msconta.model.event.ContaUpdateFailedEvent;
import br.net.dac.msconta.model.event.ContaUpdatedEvent;
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
    @RabbitListener(queues = "msconta.queue.comando")
    public void handleCommand(ComandoConta comando) {
        switch (comando.getTipo()) {
            case "CRIAR_CONTA":
                criar(comando.getPayload());
                break;
            
            case "ROLLBACK_CRIAR_CONTA":
                rollbackCriar(comando.getPayload());
                break;

            case "ATUALIZAR_CONTA":
                 atualizar(comando.getPayload());
                break;
            
            // case "ROLLBACK_ATUALIZAR_CONTA":
            //     rollbackAtualizar(comando.getPayload());
            //     break;
            
            case "DELETAR_CONTA":
                deletar(comando.getPayload());
                break;            
            
            // case "ROLLBACK_DELETAR_CONTA":
            //     rollbackDeletar(comando.getPayload());
            //     break;
            
            default:
                System.out.println("Tipo desconhecido: " + comando.getTipo());
        }
    }

        // 2. MÉTODOS DE RESPOSTA A SAGA
            // 2.1 CREATE
                // 2.2.1 CRIAÇÃO DE CONTA
    public void criar (ContaResponseDTO contaDTO) {
        try {
            // Mapeamento de ContaDTO para ContaRequestDTO
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setGerenteCpf(contaDTO.getGerenteCpf());
            requestDTO.setClienteCpf(contaDTO.getClienteCpf());
            requestDTO.setSalario(contaDTO.getSaldo());
                        
            ContaResponseDTO contaAdicionada = contaCommandService.criarConta(requestDTO);

            contaProdutor.contaCriacaoSucesso(
                new ContaCreatedEvent(
                    contaAdicionada.getNumero(),
                    contaAdicionada.getGerenteCpf(),
                    contaAdicionada.getClienteCpf(),
                    contaAdicionada.getData(),
                    contaAdicionada.getSaldo(),
                    contaAdicionada.getLimite(),
                    contaAdicionada.isAtivo()
                )
            );
        } catch (Exception e) {
            ContaCreateFailedEvent falha = new ContaCreateFailedEvent();
            falha.setCodigo("ERRO_CRIAR_CONTA");
            falha.setMensagem(e.getMessage());
            contaProdutor.contaCriacaoFalha(falha);
        }
    }
            // 2.1.2 ROLLBACK DE CONTA
    public void rollbackCriar(ContaResponseDTO contaDTO) {
        try {
            contaCommandService.desativarConta(contaDTO.getNumero());
        } catch (Exception e) {
            System.out.println("Erro no rollback de criação:  + e.getMessage()");
        }
    }

        // 2.2 UPDATE
            // 2.2.1 ATUALIZAÇÃO DE CONTA
    private void atualizar(ContaResponseDTO contaDTO) {
        try {
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setGerenteCpf(contaDTO.getGerenteCpf());
            requestDTO.setClienteCpf(contaDTO.getClienteCpf());
            requestDTO.setSalario(contaDTO.getSaldo());
            
            ContaResponseDTO atualizada = contaCommandService.atualizarConta(contaDTO.getNumero(), requestDTO);

            contaProdutor.contaUpdateSucesso(
                new ContaUpdatedEvent(
                    atualizada.getNumero(),
                    atualizada.getGerenteCpf(),
                    atualizada.getSaldo(),
                    atualizada.getLimite()
                )
            );
        } catch (Exception e) {
            ContaUpdateFailedEvent falha = new ContaUpdateFailedEvent();
            falha.setCodigo("ERRO_ATUALIZAR_CONTA");
            falha.setMensagem(e.getMessage());
            contaProdutor.contaUpdateFalha(falha);
        }
    }
            // 2.2.2 ROLLBACK DE ATUALIZAÇÃO - REQUER QUE O ESTADO ANTERIOR SEJA ENVIADO NO PAYLOAD!!!
    // private void rollbackAtualizar(ContaResponseDTO contaDTO) {
    //     try {
    //         ContaRequestDTO requestDTO = new ContaRequestDTO();
    //         requestDTO.setGerenteCpf(contaDTO.getGerenteCpf());
    //         requestDTO.setClienteCpf(contaDTO.getClienteCpf());
    //         requestDTO.setSalario(contaDTO.getSaldo());
            
    //         contaCommandService.atualizarConta(contaDTO.getNumero(), requestDTO);
    //     } catch (Exception e) {
    //         System.out.println("Erro no rollback de atualização: " + e.getMessage());
    //     }
    // }
        // 2.3.1 DELETE
            // 2.3.1 DELETE
    private void deletar(ContaResponseDTO contaDTO) {
        try {
            contaCommandService.desativarConta(contaDTO.getNumero());

            contaProdutor.contaDeleteSucesso(
                new ContaDeletedEvent(false, contaDTO.getNumero(),
                    contaDTO.getClienteCpf(), contaDTO.getGerenteCpf())
            );
        } catch (Exception e) {
            ContaDeleteFailedEvent falha = new ContaDeleteFailedEvent();
            falha.setCodigo("ERRO_DELETAR_CONTA");
            falha.setMensagem(e.getMessage());
            contaProdutor.contaDeleteFalha(falha);
        }
    }
            // 2.3.1 ROLLBACK DE DELETE
    // private void rollbackDeletar(ContaResponseDTO contaDTO) {
    //     try {
    //         ContaRequestDTO requestDTO = new ContaRequestDTO();
    //         requestDTO.setGerenteCpf(contaDTO.getGerenteCpf());
    //         requestDTO.setClienteCpf(contaDTO.getClienteCpf());
    //         requestDTO.setSalario(contaDTO.getSaldo());
            
    //         contaCommandService.atualizarConta(contaDTO.getNumero(), requestDTO);
    //     } catch (Exception e) {
    //         System.out.println("Erro no rollback de deleção: " + e.getMessage());
    //     }
    // }
}
