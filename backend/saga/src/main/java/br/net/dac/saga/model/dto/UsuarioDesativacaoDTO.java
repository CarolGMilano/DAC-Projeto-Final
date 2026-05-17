package br.net.dac.saga.model.dto;

public class UsuarioDesativacaoDTO {
  private String id;
  
  public UsuarioDesativacaoDTO() {
  }

  public UsuarioDesativacaoDTO(String id) {
    this.id = id;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  @Override
  public String toString() {
    return "UsuarioDesativacaoDTO [id=" + id + "]";
  }
}
