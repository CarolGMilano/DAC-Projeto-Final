package br.net.dac.msauth2.model.dto;

public class ComandoAuth {
  private String tipo; //CRIAR, ALTERAR, DELETAR
  private Object payload;

  public ComandoAuth(String tipo, Object payload) {
    this.tipo = tipo;
    this.payload = payload;
  }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public Object getPayload() { return payload; }
  public void setPayload(Object payload) { this.payload = payload; }
}