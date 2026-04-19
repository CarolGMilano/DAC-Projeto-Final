package br.net.dac.msgerente.model.dto;

public class GerenteResumoDTO {
  private Long idUsuario;
  private String cpf;
  private String nome;
  private String telefone;

  public GerenteResumoDTO() {}

  public GerenteResumoDTO(Long idUsuario, String cpf, String nome, String telefone) {
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.telefone = telefone;
  }

  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }
}
