package br.net.dac.mscliente2.model.event;

public class ClienteAlteracaoFalhaEvent {
  private String codigo;
  private String mensagem;

  public ClienteAlteracaoFalhaEvent(String codigo, String mensagem) {
    this.codigo = codigo;
    this.mensagem = mensagem;
  }

  public String getCodigo() { return codigo; }
  public void setCodigo(String codigo) { this.codigo = codigo; }
  
  public String getMensagem() { return mensagem; }
  public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
