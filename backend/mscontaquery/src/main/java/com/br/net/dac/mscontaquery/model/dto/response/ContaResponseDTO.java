package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDate;

import com.br.net.dac.mscontaquery.model.entity.Endereco;

public class ContaResponseDTO {
    private String numeroConta;
    private String cpfCliente;
    private String nomeCliente;
    private Endereco endereco;
    private Double saldo;
    private Double limite;
    private Double salario;
    private String email;
    private String telefone;
    private String estadoCivil;
    private Long idGerente;
    private String nomeGerente;
    private LocalDate dataCriacao;

public ContaResponseDTO() {
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

public Endereco getEndereco() {
    return endereco;
}

public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
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

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public String getEstadoCivil() {
    return estadoCivil;
}

public void setEstadoCivil(String estadoCivil) {
    this.estadoCivil = estadoCivil;
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

public LocalDate getDataCriacao() {
    return dataCriacao;
}

public void setDataCriacao(LocalDate dataCriacao) {
    this.dataCriacao = dataCriacao;
}



}