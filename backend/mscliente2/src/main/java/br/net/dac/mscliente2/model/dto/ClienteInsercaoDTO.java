package br.net.dac.mscliente2.model.dto;

import java.math.BigDecimal;

public class ClienteInsercaoDTO {
  private Long id;
  private String idUsuario;
  private String cpf;
  private String nome;
  private String telefone;
  private BigDecimal salario;
  private String endereco;
  private String cep;
  private String cidade;
  private String estado;
  private String cpfGerente;
  private String ativo;

  public ClienteInsercaoDTO(Long id, String idUsuario, String cpf, String nome, String telefone, BigDecimal salario, String endereco, String cep,
      String cidade, String estado, String cpfGerente, String ativo) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.telefone = telefone;
    this.salario = salario;
    this.endereco = endereco;
    this.cep = cep;
    this.cidade = cidade;
    this.estado = estado;
    this.cpfGerente = cpfGerente;
    this.ativo = ativo;
  }
  public ClienteInsercaoDTO() {
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getIdUsuario() {
    return idUsuario;
  }
  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

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
  public String getCpfGerente() {
    return cpfGerente;
  }
  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }
  public String getAtivo() {
    return ativo;
  }
  public void setAtivo(String ativo) {
    this.ativo = ativo;
  }
}
