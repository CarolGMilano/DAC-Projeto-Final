package br.net.dac.msconta.model.dto;

public class ComandoVinculo {
  private String tipo; //CRIAR, ALTERAR, DELETAR
  private Object payload;

  public ComandoVinculo(String tipo, Object payload) {
    this.tipo = tipo;
    this.payload = payload;
  }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public Object getPayload() { return payload; }
  public void setPayload(Object payload) { this.payload = payload; }
}
