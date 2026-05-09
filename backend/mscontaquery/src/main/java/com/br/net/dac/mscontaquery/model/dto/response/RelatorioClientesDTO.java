package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDate;

public class RelatorioClientesDTO {

private String nomeCliente;
private String cpfCliente;
private String numeroConta;
private String nomeGerente;
private Double saldo;
private Double limite;
private Double salario;
private String statusConta;
private LocalDate dataCriacao;


public RelatorioClientesDTO() {
}

public RelatorioClientesDTO(String nomeCliente, String cpfCliente, String numeroConta, String nomeGerente, Double saldo,
        Double limite, Double salario, String statusConta, LocalDate dataCriacao) {
    this.nomeCliente = nomeCliente;
    this.cpfCliente = cpfCliente;
    this.numeroConta = numeroConta;
    this.nomeGerente = nomeGerente;
    this.saldo = saldo;
    this.limite = limite;
    this.salario = salario;
    this.statusConta = statusConta;
    this.dataCriacao = dataCriacao;
}

public String getNomeCliente() {
    return nomeCliente;
}
public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}
public String getCpfCliente() {
    return cpfCliente;
}
public void setCpfCliente(String cpfCliente) {
    this.cpfCliente = cpfCliente;
}
public String getNumeroConta() {
    return numeroConta;
}
public void setNumeroConta(String numeroConta) {
    this.numeroConta = numeroConta;
}
public String getNomeGerente() {
    return nomeGerente;
}
public void setNomeGerente(String nomeGerente) {
    this.nomeGerente = nomeGerente;
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
