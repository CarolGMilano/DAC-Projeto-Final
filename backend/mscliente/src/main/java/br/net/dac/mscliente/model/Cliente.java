package br.net.dac.mscliente.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "idUsuario")
  private Long idUsuario;

  @Column(name = "cpf", unique = true, nullable = false)
  private String cpf;

  @Column(name = "nome")
  private String nome;

  @Column(name = "email")
  private String email;

  @Column(name = "telefone")
  private String telefone;

  @Column(name = "salario")
  private Double salario;

  public Cliente() {}


  public Cliente(Long id, Long idUsuario, String cpf, String nome, String email, String telefone, Double salario) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.email = email;
    this.telefone = telefone;
    this.salario = salario;
  }


  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }

  public Double getSalario() { return salario; }
  public void setSalario(Double salario) { this.salario = salario; }
}