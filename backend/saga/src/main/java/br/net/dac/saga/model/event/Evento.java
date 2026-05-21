package br.net.dac.saga.model.event;

public class Evento {
  private String tipo;
  private Object payload;

  public Evento() {
  }

  public Evento(String tipo, Object payload) {
    this.tipo = tipo;
    this.payload = payload;
  }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }

  public Object getPayload() { return payload; }
  public void setPayload(Object payload) { this.payload = payload; }

  @Override
  public String toString() {
    return "Evento [tipo=" + tipo + ", payload=" + payload + "]";
  }
}
