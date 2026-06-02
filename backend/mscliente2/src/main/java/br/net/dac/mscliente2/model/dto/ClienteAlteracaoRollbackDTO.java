package br.net.dac.mscliente2.model.dto;

public class ClienteAlteracaoRollbackDTO {
  private String cpfGerente;

  public ClienteAlteracaoRollbackDTO(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }

  public ClienteAlteracaoRollbackDTO() {
  }

  public String getCpfGerente() {
    return cpfGerente;
  }

  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }
  
}
