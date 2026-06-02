package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.dac.msconta.model.dto.ClienteAlterarGerenteDTO;
import br.net.dac.msconta.model.event.ContaCreateFailedEvent;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.event.ContaDeleteFailedEvent;
import br.net.dac.msconta.model.event.ContaDeletedEvent;
import br.net.dac.msconta.model.event.ContaUpdateFailedEvent;
import br.net.dac.msconta.model.event.ContaUpdatedEvent;
import br.net.dac.msconta.model.event.Evento;
import br.net.dac.msconta.model.event.MovimentacaoCreatedEvent;
import br.net.dac.msconta.model.event.MovimentacaoFailedEvent;
import br.net.dac.msconta.model.event.RebootContaEvent;
import br.net.dac.msconta.model.event.VinculoFalhaEvent;
import br.net.dac.msconta.model.event.VinculoSucessoEvent;

@Component
public class ContaProdutor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String CONTA_QUERY   = "msconta.queue.query";
    public static final String CLIENTE_QUERY   = "mscliente.queue.comando";

    public static final String CONTA_SUCESSO = "msconta.queue.sucesso";
    public static final String CONTA_FALHA   = "msconta.queue.falha";

// CONTA
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

    // MOVIMENTACAO
        // ABRANGE SAQUE, DEPÓSITO, TRANSFERÊNCIA
    public void movimentacaoCreateSucesso(MovimentacaoCreatedEvent payload) {
        publicar(CONTA_SUCESSO, "CRIAR_MOVIMENTACAO_SUCESSO", payload);
        publicar(CONTA_QUERY, "MOVIMENTACAO_CRIADA", payload);
    }

    public void movimentantacaoCreateFalha(MovimentacaoFailedEvent payload) {
        publicar(CONTA_FALHA, "CRIAR_MOVIMENTACAO_FALHA", payload);
    }

    public void atualizarGerenteCliente(ClienteAlterarGerenteDTO payload) {
        System.out.println("PUBLICANDO ALTERAR_GERENTE");
        publicar(CLIENTE_QUERY, "ALTERAR_GERENTE", payload);
    };

    public void iniciarReboot(RebootContaEvent payload) {
        System.out.println("PUBLICANDO INICIAR_REBOOT");
        publicar(CONTA_QUERY, "INICIAR_REBOOT", payload);
    };


    // PUBLICAR

    private void publicar(String fila, String tipo, Object payload) {
        Evento evento = new Evento();
        evento.setTipo(tipo);
        evento.setPayload(payload);
        rabbitTemplate.convertAndSend(fila, evento);
    }


    public void vinculacaoSucesso(VinculoSucessoEvent payload) {
        Evento evento = new Evento();

        evento.setTipo("VINCULAR_GERENTE_SUCESSO");
        evento.setPayload(payload);

        rabbitTemplate.convertAndSend(CONTA_SUCESSO, evento);
    }

    public void vinculacaoFalha(VinculoFalhaEvent erro) {
        Evento evento = new Evento();

        evento.setTipo("VINCULAR_GERENTE_FALHA");
        evento.setPayload(erro);

        rabbitTemplate.convertAndSend(CONTA_FALHA, evento);
    }
}
