package br.net.dac.saga.model.contexto;

public class SagaInsercaoContexto {
  private String idSaga;
  private Long id;
  private String idUsuario;
  private String cpf;
  private String nome;
  private String email;
  private String senha;
  private String tipo;
  private Boolean ativo;
  //Por conta de ser assíncrono, temos que guardar o status da saga para devolver ao frontend. Se não a mensagem de erro se perde.
  private String status;
  private String erro;
  private String codigoErro;

  public SagaInsercaoContexto() {
  }

  public SagaInsercaoContexto(String idSaga, Long id, String idUsuario, String cpf, String nome, String email, String senha, String tipo,
      Boolean ativo, String status, String erro, String codigoErro) {
    this.idSaga = idSaga;
    this.id = id;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.tipo = tipo;
    this.ativo = ativo;
    this.status = status;
    this.erro = erro;
    this.codigoErro = codigoErro;
  }

  public String getIdSaga() { return idSaga; }
  public void setIdSaga(String idSaga) {  this.idSaga = idSaga; }

  public Long getId() { return id; }
  public void setId(Long id) {  this.id = id; }

  public String getIdUsuario() { return idUsuario; }
  public void setIdUsuario(String idUsuario) {  this.idUsuario = idUsuario; }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getErro() {
    return erro;
  }

  public void setErro(String erro) {
    this.erro = erro;
  }

  public String getCodigoErro() {
    return codigoErro;
  }

  public void setCodigoErro(String codigoErro) {
    this.codigoErro = codigoErro;
  }

  @Override
  public String toString() {
    return "SagaInsercaoContexto [idSaga=" + idSaga + ", id=" + id + ", idUsuario=" + idUsuario + ", cpf=" + cpf + ", nome=" + nome
        + ", email=" + email + ", senha=" + senha + ", tipo=" + tipo + ", ativo=" + ativo + ", status=" + status
        + ", erro=" + erro + ", codigoErro=" + codigoErro + "]";
  }
}