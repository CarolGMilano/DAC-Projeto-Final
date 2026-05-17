package br.net.dac.saga.model.dto;

public class GerenteCriacaoResumoDTO {
  private Long id;
  private String cpf;
  
  public GerenteCriacaoResumoDTO(Long id, String cpf) {
    this.id = id;
    this.cpf = cpf;
  }
  public GerenteCriacaoResumoDTO() {
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getCpf() {
    return cpf;
  }
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
  @Override
  public String toString() {
    return "GerenteCriacaoResumoDTO [id=" + id + ", cpf=" + cpf + "]";
  }

}
