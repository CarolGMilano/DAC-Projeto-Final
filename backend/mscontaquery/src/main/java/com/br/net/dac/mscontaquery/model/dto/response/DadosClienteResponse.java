package com.br.net.dac.mscontaquery.model.dto.response;

public class DadosClienteResponse {
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private Double salario;
    private String conta;
    private Double saldo;
    private Double limite;
    private String gerente;
    private String GerenteNome;
    private String gerenteEmail;

public DadosClienteResponse() {}

public String getCpf() {
    return cpf;
}

public void setCpf(String cpf) {
    this.cpf = cpf;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getEndereco() {
    return endereco;
}

public void setEndereco(String endereco) {
    this.endereco = endereco;
}

public String getCidade() {
    return cidade;
}

public void setCidade(String cidade) {
    this.cidade = cidade;
}

public String getEstado() {
    return estado;
}

public void setEstado(String estado) {
    this.estado = estado;
}

public Double getSalario() {
    return salario;
}

public void setSalario(Double salario) {
    this.salario = salario;
}

public String getConta() {
    return conta;
}

public void setConta(String conta) {
    this.conta = conta;
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



public String getGerenteNome() {
    return GerenteNome;
}

public void setGerenteNome(String gerenteNome) {
    GerenteNome = gerenteNome;
}

public String getGerenteEmail() {
    return gerenteEmail;
}

public void setGerenteEmail(String gerenteEmail) {
    this.gerenteEmail = gerenteEmail;
}

public String getGerente() {
    return gerente;
}

public void setGerente(String gerente) {
    this.gerente = gerente;
}



}