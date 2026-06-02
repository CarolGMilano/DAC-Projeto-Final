package br.net.dac.msconta.model.event;

import java.util.List;

import br.net.dac.msconta.model.dto.MovimentacaoRequestDTO;

public class RebootContaEvent {
    private List<ContaCreatedEvent> contas;
    private List<MovimentacaoRequestDTO> movimentacoes;

    public RebootContaEvent() {}

    public RebootContaEvent(List<ContaCreatedEvent> contas, List<MovimentacaoRequestDTO> movimentacoes) {
        this.contas = contas;
        this.movimentacoes = movimentacoes;
    }

    public List<ContaCreatedEvent> getContas() {
        return contas;
    }

    public void setContas(List<ContaCreatedEvent> contas) {
        this.contas = contas;
    }

    public List<MovimentacaoRequestDTO> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoRequestDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}