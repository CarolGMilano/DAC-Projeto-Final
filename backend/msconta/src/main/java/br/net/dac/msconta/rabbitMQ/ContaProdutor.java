package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msconta.model.event.ContaCreateFailedEvent;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.event.ContaDeleteFailedEvent;
import br.net.dac.msconta.model.event.ContaDeletedEvent;
import br.net.dac.msconta.model.event.ContaUpdateFailedEvent;
import br.net.dac.msconta.model.event.ContaUpdatedEvent;
import br.net.dac.msconta.model.event.Evento;

// É o EventDispatcher do MSConta
@Component
public class ContaProdutor {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public static final String CONTA_SUCESSO = "msconta.queue.sucesso";
    public static final String CONTA_FALHA = "msconta.queue.falha";

    // 1. CRIACAO
        // 1.1 CRIACAO SUCESSO
    public void contaCriacaoSucesso(ContaCreatedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("CRIAR_CONTA_SUCESSO");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_SUCESSO, evento);
    }    
    
        // 1.2 CRIACAO FALHA
    public void contaCriacaoFalha(ContaCreateFailedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("CRIAR_CONTA_FALHA");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_FALHA, evento);
    }

    // 2. UPDATE
        // 2.1 UPDATE SUCESSO
    public void contaUpdateSucesso(ContaUpdatedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("ATUALIZAR_CONTA_SUCESSO");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_SUCESSO, evento);
    }
    
        //2.2 UPDATE FALHA
    public void contaUpdateFalha(ContaUpdateFailedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("ATUALIZAR_CONTA_FALHA");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_FALHA, evento);
    }   

    // 3. UPDATE
        // 3.1 DELETE SUCESSO
    public void contaDeleteSucesso(ContaDeletedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("DELETAR_CONTA_SUCESSO");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_SUCESSO, evento);
    }
    
        //2.2 DELETE FALHA
    public void contaDeleteFalha(ContaDeleteFailedEvent payload) {
        Evento evento = new Evento();
        evento.setTipo("DELETAR_CONTA_FALHA");
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(CONTA_FALHA, evento);
    }
}