package br.net.dac.saga.model.dto;

public class SagaRespostaDTO {
  private String status;
  private String codigoErro;
  private String mensagem;

  public SagaRespostaDTO(String status, String codigoErro, String mensagem) {
    this.status = status;
    this.codigoErro = codigoErro;
    this.mensagem = mensagem;
  }

  public SagaRespostaDTO() {
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCodigoErro() {
    return codigoErro;
  }

  public void setCodigoErro(String codigoErro) {
    this.codigoErro = codigoErro;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  
}
