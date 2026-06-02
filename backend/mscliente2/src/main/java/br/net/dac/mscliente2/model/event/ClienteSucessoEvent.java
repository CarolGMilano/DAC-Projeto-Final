package br.net.dac.mscliente2.model.event;

public class ClienteSucessoEvent {
  private String cpfGerente;

  public ClienteSucessoEvent() {}

  public ClienteSucessoEvent(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }

  public String getCpfGerente() { return cpfGerente; }
  public void setCpfGerente(String cpfGerente) { this.cpfGerente = cpfGerente; }
}