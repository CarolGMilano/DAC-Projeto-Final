package br.net.dac.mscliente2.model.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cliente")
public class Cliente {
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

  @Column(name = "telefone", nullable = false)
  private String telefone;
  
  @Column(name = "salario", nullable = false)
  private BigDecimal salario;
  
  @Column(name = "endereco", nullable = false)
  private String endereco;
  
  @Column(name = "cep", nullable = false)
  private String cep;
  
  @Column(name = "cidade", nullable = false)
  private String cidade;
  
  @Column(name = "estado", nullable = false)
  private String estado;
  
  @Column(name = "motivorejeicao")
  private String motivoRejeicao;
  
  @Column(name = "dataresposta")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime dataResposta;

  @Column(name = "cpfgerente", nullable = false)
  private String cpfGerente;
  
  @Column(name = "ativo", nullable = false)
  private String ativo;

  public Cliente() {}

  public Cliente(Long id, String idUsuario, String cpf, String nome, String telefone, BigDecimal salario, String endereco, String cep, String cidade, String estado, String motivoRejeicao, LocalDateTime dataResposta, String ativo) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.telefone = telefone;
    this.salario = salario;
    this.endereco = endereco;
    this.cep = cep;
    this.cidade = cidade;
    this.estado = estado;
    this.motivoRejeicao = motivoRejeicao;
    this.dataResposta = dataResposta;
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
  
  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }


  public BigDecimal getSalario() {
    return salario;
  }

  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getMotivoRejeicao() {
    return motivoRejeicao;
  }

  public void setMotivoRejeicao(String motivoRejeicao) {
    this.motivoRejeicao = motivoRejeicao;
  }

  public LocalDateTime getDataResposta() {
    return dataResposta;
  }

  public void setDataResposta(LocalDateTime dataResposta) {
    this.dataResposta = dataResposta;
  }

  public String getCpfGerente() {
    return cpfGerente;
  }

  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }

  public String getAtivo() { return ativo; }
  public void setAtivo(String ativo) { this.ativo = ativo; }
}
