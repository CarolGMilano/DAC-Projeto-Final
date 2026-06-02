package com.br.net.dac.mscontaquery.model.event;

import java.util.List;

import com.br.net.dac.mscontaquery.model.dto.MovimentacaoRequestDTO;

public class RebootContaEvent {
    private List<ContaRebootEvent> contas;
    private List<MovimentacaoRequestDTO> movimentacoes;

    public RebootContaEvent() {}

    public RebootContaEvent(List<ContaRebootEvent> contas, List<MovimentacaoRequestDTO> movimentacoes) {
        this.contas = contas;
        this.movimentacoes = movimentacoes;
    }

    public List<ContaRebootEvent> getContas() {
        return contas;
    }

    public void setContas(List<ContaRebootEvent> contas) {
        this.contas = contas;
    }

    public List<MovimentacaoRequestDTO> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoRequestDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}