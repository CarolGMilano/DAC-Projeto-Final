package br.net.dac.saga.model.dto;

import java.math.BigDecimal;

public class AutocadastroDTO {
  private String cpf;
  private String cpfGerente;
  private String email;
  private String nome;
  private String telefone;
  private BigDecimal salario;
  private String endereco;
  private String cep;
  private String cidade;
  private String estado;

  public AutocadastroDTO() {
  }

  public AutocadastroDTO(String cpf, String cpfGerente, String email, String nome, String telefone, BigDecimal salario,
      String endereco, String cep, String cidade, String estado) {
    this.cpf = cpf;
    this.cpfGerente = cpfGerente;
    this.email = email;
    this.nome = nome;
    this.telefone = telefone;
    this.salario = salario;
    this.endereco = endereco;
    this.cep = cep;
    this.cidade = cidade;
    this.estado = estado;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCpfGerente() {
    return cpfGerente;
  }

  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
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

  @Override
  public String toString() {
    return "AutocadastroDTO [cpf=" + cpf + ", cpfGerente=" + cpfGerente + ", email=" + email + ", nome=" + nome
        + ", telefone=" + telefone + ", salario=" + salario + ", endereco=" + endereco + ", cep=" + cep + ", cidade="
        + cidade + ", estado=" + estado + "]";
  }
}