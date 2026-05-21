package br.net.dac.saga.model.dto;

public class GerenteDTO {
  private String cpf;
  private String nome;
  private String email;
  private String tipo;
  private String senha;

  public GerenteDTO() {}

  public GerenteDTO(String cpf, String nome, String email, String tipo, String senha) {
    this.cpf = cpf;
    this.nome = nome;
    this.email = email;
    this.tipo = tipo;
    this.senha = senha;
  }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  
  public String getTipo() { return tipo; }
  public void setTipo(String tipo) { this.tipo = tipo; }

  public String getSenha() { return senha; }
  public void setSenha(String senha) { this.senha = senha; }

  @Override
  public String toString() {
    return "GerenteDTO [cpf=" + cpf + ", nome=" + nome + ", email=" + email + ", tipo=" + tipo + ", senha=" + senha
        + "]";
  }
}