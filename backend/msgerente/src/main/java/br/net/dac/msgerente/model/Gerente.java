package br.net.dac.msgerente.model;
import jakarta.persistence.*;

@Entity
@Table(name = "gerente")
public class Gerente {
  @Id
  //Isso é pra dizer que o id vai ser gerado pelo banco automaticamente
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "idUsuario")
  private Long idUsuario;

  @Column(name = "cpf")
  private String cpf;

  @Column(name = "nome")
  private String nome;

  @Column(name = "telefone")
  private String telefone;

  @Column(name = "ativo")
  private Boolean ativo;

  public Gerente() {}

  public Gerente(Long id, Long idUsuario, String cpf, String nome, String telefone, Boolean ativo) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.telefone = telefone;
    this.ativo = ativo;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }

  public Boolean getAtivo() { return ativo; }
  public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}