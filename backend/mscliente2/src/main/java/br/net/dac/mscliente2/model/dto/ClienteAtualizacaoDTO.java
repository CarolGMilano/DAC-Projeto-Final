package br.net.dac.mscliente2.model.dto;

import java.math.BigDecimal;

public class ClienteAtualizacaoDTO {
  private String cpf;
  private String nome;
  private BigDecimal salario;
  private String cep;
  private String endereco;
  private String cidade;
  private String estado;
  public ClienteAtualizacaoDTO() {
  }
  public ClienteAtualizacaoDTO(String cpf, String nome, BigDecimal salario, String cep, String endereco, String cidade,
      String estado) {
    this.cpf = cpf;
    this.nome = nome;
    this.salario = salario;
    this.cep = cep;
    this.endereco = endereco;
    this.cidade = cidade;
    this.estado = estado;
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
  public String getCep() {
    return cep;
  }
  public void setCep(String cep) {
    this.cep = cep;
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
  public String getCpf() {
    return cpf;
  }
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
}
