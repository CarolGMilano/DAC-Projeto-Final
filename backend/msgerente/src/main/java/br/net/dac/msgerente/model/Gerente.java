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

  @Column(name = "idusuario", unique = true, nullable = false)
  private String idUsuario;

  @Column(name = "cpf", unique = true, nullable = false)
  private String cpf;

  @Column(name = "nome", nullable = false)
  private String nome;
  /*
    @Column(name = "telefone", nullable = false)
    private String telefone;
  */

  @Column(name = "ativo", nullable = false)
  private String ativo;

  public Gerente() {}

  public Gerente(Long id, String idUsuario, String cpf, String nome, String ativo) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    //this.telefone = telefone;
    this.ativo = ativo;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getIdUsuario() { return idUsuario; }
  public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }
  
  /*
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
   */

  public String getAtivo() { return ativo; }
  public void setAtivo(String ativo) { this.ativo = ativo; }
}