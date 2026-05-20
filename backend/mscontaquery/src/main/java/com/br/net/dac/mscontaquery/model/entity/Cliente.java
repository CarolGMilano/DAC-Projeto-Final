package com.br.net.dac.mscontaquery.model.entity;

import java.math.BigDecimal;

public class Cliente {
private Long id;
private String cpf;
private Integer idUsuario;
private String nome;
private BigDecimal salario;
private String endereco;
private String cidade;
private String estado;
private String email;
private String telefone;

public Cliente(){}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getCpf() {
    return cpf;
}

public void setCpf(String cpf) {
    this.cpf = cpf;
}

public Integer getIdUsuario() {
    return idUsuario;
}

public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public BigDecimal getSalario() {
    return salario;
}

public void setSalario(BigDecimal salario) {
    this.salario = salario;
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

}
