package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msconta.model.dto.ComandoConta;
import br.net.dac.msconta.model.dto.ContaDTO;
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
            
            case "ROLLBACK_ATUALIZAR_CONTA":
                rollbackAtualizar(comando.getPayload());
                break;
            
            case "DELETAR_CONTA":
                deletar(comando.getPayload());
                break;            
            
            case "ROLLBACK_DELETAR_CONTA":
                rollbackDeletar(comando.getPayload());
                break;
            
            default:
                System.out.println("Tipo desconhecido: " + comando.getTipo());
        }
    }

        // 2. MÉTODOS DE RESPOSTA A SAGA
            // 2.1 CREATE
                // 2.2.1 CRIAÇÃO DE CONTA
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
                    contaAdicionada.getNumeroConta(), contaAdicionada.getSaldo(), contaAdicionada.getLimite()
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
    public void rollbackCriar(ContaDTO contaDTO) {
        try {
            contaCommandService.desativarConta(contaDTO.getNumeroConta());
        } catch (Exception e) {
            System.out.println("Erro no rollback de criação:  + e.getMessage()");
        }
    }

        // 2.2 UPDATE
            // 2.2.1 ATUALIZAÇÃO DE CONTA
    private void atualizar(ContaDTO contaDTO) {
        try {
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setAtivo(contaDTO.isAtivo());
            requestDTO.setIdGerente(contaDTO.getIdGerente());
            requestDTO.setIdCliente(contaDTO.getIdCliente());
            requestDTO.setSaldo(contaDTO.getSaldo());
            requestDTO.setLimite(contaDTO.getLimite());

            ContaResponseDTO atualizada = contaCommandService.atualizarConta(contaDTO.getNumeroConta(), requestDTO);

            contaProdutor.contaUpdateSucesso(
                new ContaUpdatedEvent(atualizada.getNumeroConta(), atualizada.isAtivo())
            );
        } catch (Exception e) {
            ContaUpdateFailedEvent falha = new ContaUpdateFailedEvent();
            falha.setCodigo("ERRO_ATUALIZAR_CONTA");
            falha.setMensagem(e.getMessage());
            contaProdutor.contaUpdateFalha(falha);
        }
    }
            // 2.2.2 ROLLBACK DE ATUALIZAÇÃO - REQUER QUE O ESTADO ANTERIOR SEJA ENVIADO NO PAYLOAD!!!
    private void rollbackAtualizar(ContaDTO contaDTO) {
        try {
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setAtivo(contaDTO.isAtivo());
            requestDTO.setIdGerente(contaDTO.getIdGerente());
            requestDTO.setIdCliente(contaDTO.getIdCliente());
            requestDTO.setSaldo(contaDTO.getSaldo());
            requestDTO.setLimite(contaDTO.getLimite());

            contaCommandService.atualizarConta(contaDTO.getNumeroConta(), requestDTO);
        } catch (Exception e) {
            System.out.println("Erro no rollback de atualização: " + e.getMessage());
        }
    }
        // 2.3.1 DELETE
            // 2.3.1 DELETE
    private void deletar(ContaDTO contaDTO) {
        try {
            contaCommandService.desativarConta(contaDTO.getNumeroConta());

            contaProdutor.contaDeleteSucesso(
                new ContaDeletedEvent(false, contaDTO.getNumeroConta(),
                    contaDTO.getIdCliente(), contaDTO.getIdGerente())
            );
        } catch (Exception e) {
            ContaDeleteFailedEvent falha = new ContaDeleteFailedEvent();
            falha.setCodigo("ERRO_DELETAR_CONTA");
            falha.setMensagem(e.getMessage());
            contaProdutor.contaDeleteFalha(falha);
        }
    }
            // 2.3.1 ROLLBACK DE DELETE
    private void rollbackDeletar(ContaDTO contaDTO) {
        try {
            ContaRequestDTO requestDTO = new ContaRequestDTO();
            requestDTO.setAtivo(true); // reativa
            requestDTO.setIdGerente(contaDTO.getIdGerente());
            requestDTO.setIdCliente(contaDTO.getIdCliente());
            requestDTO.setSaldo(contaDTO.getSaldo());
            requestDTO.setLimite(contaDTO.getLimite());

            contaCommandService.atualizarConta(contaDTO.getNumeroConta(), requestDTO);
        } catch (Exception e) {
            System.out.println("Erro no rollback de deleção: " + e.getMessage());
        }
    }
}
