package com.br.net.dac.mscontaquery.model.dto;

public class SaldoResponseDTO {
    private Double saldo;
    private String cliente;
    private String conta;

    public SaldoResponseDTO() {
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getConta() {
        return conta;
    }
    public void setConta(String conta) {
        this.conta = conta;
    }
    
}
