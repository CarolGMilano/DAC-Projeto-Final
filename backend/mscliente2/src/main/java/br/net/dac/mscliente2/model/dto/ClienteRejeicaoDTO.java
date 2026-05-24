package br.net.dac.mscliente2.model.dto;

public class ClienteRejeicaoDTO {
  private String idUsuario;
  private String cpf;
  private String ativo;

  public ClienteRejeicaoDTO(String idUsuario, String cpf, String ativo) {
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.ativo = ativo;
  }
  public ClienteRejeicaoDTO() {
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

  public String getAtivo() {
    return ativo;
  }
  public void setAtivo(String ativo) {
    this.ativo = ativo;
  }
}
