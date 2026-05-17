package br.net.dac.saga.model.dto;

public class GerenteRollbackDTO {
  private Long id;
  private String idUsuario;
  private String cpf;
  private String nome;
  //private String telefone;
  private Boolean ativo;

  public GerenteRollbackDTO() {}

  public GerenteRollbackDTO(Long id, String idUsuario, String cpf, String nome, Boolean ativo) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    //this.telefone = telefone;
    this.ativo = ativo;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

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

  public Boolean getAtivo() { return ativo; }
  public void setAtivo(Boolean ativo) { this.ativo = ativo; }

  @Override
  public String toString() {
    return "GerenteRollbackDTO [id=" + id + ", idUsuario=" + idUsuario + ", cpf=" + cpf + ", nome=" + nome + ", ativo="
        + ativo + "]";
  }
}

