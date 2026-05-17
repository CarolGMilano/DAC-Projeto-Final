package br.net.dac.saga.model.dto;

public class UsuarioCriacaoDTO {
  private String email;
  private String senha;
  private String tipo;
  
  public UsuarioCriacaoDTO() {
  }

  public UsuarioCriacaoDTO(String email, String senha, String tipo) {
    this.email = email;
    this.senha = senha;
    this.tipo = tipo;
  }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getSenha() { return senha; }
  public void setSenha(String senha) { this.senha = senha; }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }

  @Override
  public String toString() {
    return "UsuarioCriacaoDTO [email=" + email + ", senha=" + senha + ", tipo=" + tipo + "]";
  }
}
