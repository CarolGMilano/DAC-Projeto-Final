package br.net.dac.msauth2.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
  @Id
  private String id;
  private String email;
  private String senha;
  private String salt;
  private String tipo;
  private String ativo;

  public Usuario() {}

  public Usuario(String id, String email, String senha, String salt, String tipo, String ativo) {
    this.id = id;
    this.email = email;
    this.senha = senha;
    this.salt = salt;
    this.tipo = tipo;
    this.ativo = ativo;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getSenha() { return senha; }
  public void setSenha(String senha) { this.senha = senha; }

  public String getSalt() { return salt; }
  public void setSalt(String salt) { this.salt = salt; }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }

  public String getAtivo() { return ativo; }
  public void setAtivo (String ativo) { this.ativo = ativo; }
}