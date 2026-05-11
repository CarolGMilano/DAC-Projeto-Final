package br.net.dac.msgerente.model.dto;

public class ComandoGerente {
  private String tipo; //CRIAR, ALTERAR, DELETAR
  private GerenteDTO payload;

  public ComandoGerente(String tipo, GerenteDTO payload) {
    this.tipo = tipo;
    this.payload = payload;
  }

  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }
  
  public GerenteDTO getPayload() { return payload; }
  public void setPayload(GerenteDTO payload) { this.payload = payload; }
}