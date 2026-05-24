package br.net.dac.msauth2.model.dto;

public class UsuarioAprovacaoRejeicaoDTO {
  private String id;
  
  public UsuarioAprovacaoRejeicaoDTO() {
  }

  public UsuarioAprovacaoRejeicaoDTO(String id) {
    this.id = id;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
}
