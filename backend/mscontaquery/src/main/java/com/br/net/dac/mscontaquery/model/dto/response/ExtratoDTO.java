package com.br.net.dac.mscontaquery.model.dto.response;

import java.util.List;

public class ExtratoDTO {

    private String numeroConta;
    private String nomeCliente;
    private Double saldoAtual;

    public ExtratoDTO() {
    }
    
    public ExtratoDTO(String numeroConta, String nomeCliente, Double saldoAtual, List<MovimentacaoDTO> movimentacoes) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.saldoAtual = saldoAtual;
        this.movimentacoes = movimentacoes;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setSaldoAtual(Double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public void setMovimentacoes(List<MovimentacaoDTO> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    private List<MovimentacaoDTO> movimentacoes;

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public Double getSaldoAtual() {
        return saldoAtual;
    }

    public List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    
}