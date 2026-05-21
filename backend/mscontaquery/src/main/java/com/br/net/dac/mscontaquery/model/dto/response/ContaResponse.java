package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDate;

public class ContaResponse {
    private String cliente;
    private String numero;
    private Double saldo;
    private Double limite;
    private String gerente;
    private LocalDate criacao;

    public ContaResponse() {}

    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Double getLimite() {
        return limite;
    }
    public void setLimite(Double limite) {
        this.limite = limite;
    }
    public String getGerente() {
        return gerente;
    }
    public void setGerente(String gerente) {
        this.gerente = gerente;
    }
    public LocalDate getCriacao() {
        return criacao;
    }
    public void setCriacao(LocalDate criacao) {
        this.criacao = criacao;
    }

    
}
