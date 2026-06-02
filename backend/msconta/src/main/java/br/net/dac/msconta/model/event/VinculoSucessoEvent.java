package br.net.dac.msconta.model.event;

public class VinculoSucessoEvent {
  private String cpf;

  public VinculoSucessoEvent(String cpf) {
    this.cpf = cpf;
  }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }
}
