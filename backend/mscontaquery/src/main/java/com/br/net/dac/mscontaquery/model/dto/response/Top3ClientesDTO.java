package com.br.net.dac.mscontaquery.model.dto.response;

public class Top3ClientesDTO {
private String nomeCliente;
private String numeroConta;
private Double saldo;
private Double limite;
private String nomeGerente;

public Top3ClientesDTO() {
}

public Top3ClientesDTO(String nomeCliente, String numeroConta, Double saldo, Double limite, String nomeGerente) {
    this.nomeCliente = nomeCliente;
    this.numeroConta = numeroConta;
    this.saldo = saldo;
    this.limite = limite;
    this.nomeGerente = nomeGerente;
}

public String getNomeCliente() {
    return nomeCliente;
}

public String getNumeroConta() {
    return numeroConta;
}

public Double getSaldo() {
    return saldo;
}

public Double getLimite() {
    return limite;
}

public String getNomeGerente() {
    return nomeGerente;
}

public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}

public void setNumeroConta(String numeroConta) {
    this.numeroConta = numeroConta;
}

public void setSaldo(Double saldo) {
    this.saldo = saldo;
}

public void setLimite(Double limite) {
    this.limite = limite;
}

public void setNomeGerente(String nomeGerente) {
    this.nomeGerente = nomeGerente;
}



}
