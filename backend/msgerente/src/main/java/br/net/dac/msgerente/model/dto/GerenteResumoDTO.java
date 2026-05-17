package br.net.dac.msgerente.model.dto;

public class GerenteResumoDTO {
  private String idUsuario;
  private String cpf;
  private String nome;
  //private String telefone;

  public GerenteResumoDTO() {}

  public GerenteResumoDTO(String idUsuario, String cpf, String nome) {
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    //this.telefone = telefone;
  }

  public String getIdUsuario() { return idUsuario; }
  public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  /*
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
  */
}
