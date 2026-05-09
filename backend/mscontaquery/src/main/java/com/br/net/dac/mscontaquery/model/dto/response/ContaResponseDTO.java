package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDate;

public class ContaResponseDTO {
private String numeroConta;

private String cpfCliente;
private String nomeCliente;

private Double saldo;
private Double limite;
private Double salario;

private Long idGerente;
private String nomeGerente;

private String statusConta;

private LocalDate dataCriacao;

public ContaResponseDTO() {
}

public ContaResponseDTO(String numeroConta, String cpfCliente, String nomeCliente, Double saldo, Double limite,
        Double salario, Long idGerente, String nomeGerente, String statusConta, LocalDate dataCriacao) {
    this.numeroConta = numeroConta;
    this.cpfCliente = cpfCliente;
    this.nomeCliente = nomeCliente;
    this.saldo = saldo;
    this.limite = limite;
    this.salario = salario;
    this.idGerente = idGerente;
    this.nomeGerente = nomeGerente;
    this.statusConta = statusConta;
    this.dataCriacao = dataCriacao;
}

public String getNumeroConta() {
    return numeroConta;
}

public void setNumeroConta(String numeroConta) {
    this.numeroConta = numeroConta;
}

public String getCpfCliente() {
    return cpfCliente;
}

public void setCpfCliente(String cpfCliente) {
    this.cpfCliente = cpfCliente;
}

public String getNomeCliente() {
    return nomeCliente;
}

public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}

public Double getSaldo() {
    return saldo;
}

public void setSaldo(Double saldo) {
    this.saldo = saldo;
}

public Double getLimite() {
    return limite;
}

public void setLimite(Double limite) {
    this.limite = limite;
}

public Double getSalario() {
    return salario;
}

public void setSalario(Double salario) {
    this.salario = salario;
}

public Long getIdGerente() {
    return idGerente;
}

public void setIdGerente(Long idGerente) {
    this.idGerente = idGerente;
}

public String getNomeGerente() {
    return nomeGerente;
}

public void setNomeGerente(String nomeGerente) {
    this.nomeGerente = nomeGerente;
}

public String getStatusConta() {
    return statusConta;
}

public void setStatusConta(String statusConta) {
    this.statusConta = statusConta;
}

public LocalDate getDataCriacao() {
    return dataCriacao;
}

public void setDataCriacao(LocalDate dataCriacao) {
    this.dataCriacao = dataCriacao;
}



}