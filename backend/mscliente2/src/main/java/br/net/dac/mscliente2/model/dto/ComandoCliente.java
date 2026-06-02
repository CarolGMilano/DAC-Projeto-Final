package br.net.dac.mscliente2.model.dto;

public class ComandoCliente {
  private String tipo; //CRIAR, ALTERAR, DELETAR
  private Object payload;

  public ComandoCliente(String tipo, Object payload) {
    this.tipo = tipo;
    this.payload = payload;
  }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public Object getPayload() { return payload; }
  public void setPayload(Object payload) { this.payload = payload; }
}