package br.net.dac.mscliente2.model.dto;

public class RebootResponseDTO {
  public String id;
  public String email;
  
  public RebootResponseDTO() {
  }

  public RebootResponseDTO(String id, String email) {
    this.id = id;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}