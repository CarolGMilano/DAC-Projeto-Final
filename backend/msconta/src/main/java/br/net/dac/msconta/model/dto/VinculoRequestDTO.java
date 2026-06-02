package br.net.dac.msconta.model.dto;

public class VinculoRequestDTO {
  private Long id;
  private String cpf;

  public VinculoRequestDTO() {
  }
  
  public VinculoRequestDTO(Long id, String cpf) {
    this.id = id;
    this.cpf = cpf;
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
    return "VinculoRequestDTO [id=" + id + ", cpf=" + cpf + "]";
  }
}
