package br.net.dac.mscliente2.model.dto;

import java.math.BigDecimal;

public class ClienteAprovacaoDTO {
  private String idUsuario;
  private String cpf;
  private String cpfGerente;
  private BigDecimal salario;
  private String ativo;

  public ClienteAprovacaoDTO(String idUsuario, String cpf, String cpfGerente, BigDecimal salario, String ativo) {
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.cpfGerente = cpfGerente;
    this.salario = salario;
    this.ativo = ativo;
  }
  public ClienteAprovacaoDTO() {
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

  public String getCpfGerente() {
    return cpfGerente;
  }
  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }

  public BigDecimal getSalario() {
    return salario;
  }
  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }

  public String getAtivo() {
    return ativo;
  }
  public void setAtivo(String ativo) {
    this.ativo = ativo;
  }
}
