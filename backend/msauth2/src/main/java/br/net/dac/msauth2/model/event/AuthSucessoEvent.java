package br.net.dac.msauth2.model.event;

public class AuthSucessoEvent {
  private String id;
  private String email;
  private String tipo;

  public AuthSucessoEvent() {}

  public AuthSucessoEvent(String id, String email, String tipo) {
    this.id = id;
    this.email = email;
    this.tipo = tipo;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }
}