package br.net.dac.msauth2.model.dto;

public class UsuarioAlteracaoDTO {
  private String id;
  private String email;
  private String senha;
  
  public UsuarioAlteracaoDTO() {
  }

  public UsuarioAlteracaoDTO(String id, String email, String senha) {
    this.id = id;
    this.email = email;
    this.senha = senha;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getSenha() { return senha; }
  public void setSenha(String senha) { this.senha = senha; }
}
