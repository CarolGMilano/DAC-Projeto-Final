package br.net.dac.saga.model.dto;

public class AutocadastroRespostaDTO {
  private String cpf;
  private String email;

  public AutocadastroRespostaDTO() {
  }
  
  public AutocadastroRespostaDTO(String cpf, String email) {
    this.cpf = cpf;
    this.email = email;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "AutocadastroRespostaDTO [cpf=" + cpf + ", email=" + email + "]";
  }
}
