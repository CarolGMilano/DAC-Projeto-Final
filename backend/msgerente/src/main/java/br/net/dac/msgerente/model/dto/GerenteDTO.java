package br.net.dac.msgerente.model.dto;

public class GerenteDTO {
  private Long id;
  private String idUsuario;
  private String cpf;
  private String nome;
  //private String telefone;
  private String ativo;

  public GerenteDTO() {}

  public GerenteDTO(Long id, String idUsuario, String cpf, String nome, String ativo) {
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

  public String getAtivo() { return ativo; }
  public void setAtivo(String ativo) { this.ativo = ativo; }
}