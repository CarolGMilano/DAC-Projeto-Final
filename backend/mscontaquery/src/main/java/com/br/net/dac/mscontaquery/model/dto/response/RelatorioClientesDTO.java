package com.br.net.dac.mscontaquery.model.dto.response;

public class RelatorioClientesDTO {

private String nomeCliente;
private String cpfCliente;
private String email;
private String numeroConta;
private String nomeGerente;
private String cpfGerente;
private Double saldo;
private Double limite;
private Double salario;

public RelatorioClientesDTO() {
}

public RelatorioClientesDTO(String nomeCliente, String cpfCliente, String email, String numeroConta, String nomeGerente,
        String cpfGerente, Double saldo, Double limite, Double salario) {
    this.nomeCliente = nomeCliente;
    this.cpfCliente = cpfCliente;
    this.email = email;
    this.numeroConta = numeroConta;
    this.nomeGerente = nomeGerente;
    this.cpfGerente = cpfGerente;
    this.saldo = saldo;
    this.limite = limite;
    this.salario = salario;
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

public String getCpfGerente() {
    return cpfGerente;
}

public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
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

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}


}
