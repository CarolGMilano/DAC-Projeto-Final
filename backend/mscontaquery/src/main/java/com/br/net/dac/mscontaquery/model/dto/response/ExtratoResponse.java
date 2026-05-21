package com.br.net.dac.mscontaquery.model.dto.response;

import java.util.List;

public class ExtratoResponse {

    private String conta;
    private Double saldo;
    private List<ItemExtratoResponse> movimentacoes;

    public ExtratoResponse() {}

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<ItemExtratoResponse> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<ItemExtratoResponse> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }
    
    
}