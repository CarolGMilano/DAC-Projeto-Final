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

@Component
public class ContaProdutor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String CONTA_SUCESSO = "msconta.queue.sucesso";
    public static final String CONTA_FALHA   = "msconta.queue.falha";
    public static final String CONTA_QUERY   = "msconta.queue.query";

    // CREATE

    public void contaCriacaoSucesso(ContaCreatedEvent payload) {
        publicar(CONTA_SUCESSO, "CRIAR_CONTA_SUCESSO", payload);
        publicar(CONTA_QUERY,   "CONTA_CRIADA",        payload); // sincroniza BD de leitura
    }

    public void contaCriacaoFalha(ContaCreateFailedEvent payload) {
        publicar(CONTA_FALHA, "CRIAR_CONTA_FALHA", payload);
    }

    // UPDATE

    public void contaUpdateSucesso(ContaUpdatedEvent payload) {
        publicar(CONTA_SUCESSO, "ATUALIZAR_CONTA_SUCESSO", payload);
        publicar(CONTA_QUERY,   "CONTA_ATUALIZADA",        payload); // sincroniza BD de leitura
    }

    public void contaUpdateFalha(ContaUpdateFailedEvent payload) {
        publicar(CONTA_FALHA, "ATUALIZAR_CONTA_FALHA", payload);
    }

    // DELETE

    public void contaDeleteSucesso(ContaDeletedEvent payload) {
        publicar(CONTA_SUCESSO, "DELETAR_CONTA_SUCESSO", payload);
        publicar(CONTA_QUERY,   "CONTA_DESATIVADA",      payload); // sincroniza BD de leitura
    }

    public void contaDeleteFalha(ContaDeleteFailedEvent payload) {
        publicar(CONTA_FALHA, "DELETAR_CONTA_FALHA", payload);
    }

    // PUBLICAR

    private void publicar(String fila, String tipo, Object payload) {
        Evento evento = new Evento();
        evento.setTipo(tipo);
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(fila, evento);
    }
}
