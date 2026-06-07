package br.net.dac.saga.model.contexto;

import java.math.BigDecimal;

public class SagaAutocadastroContexto {
  private String idSaga;
  private String cpfGerente;
  private String idUsuario;
  private String cpf;
  private String email;
  private String nome;
  private String telefone;
  private BigDecimal salario;
  private String endereco;
  private String cep;
  private String cidade;
  private String estado;
  private String tipo;
  private String ativo;
  //Por conta de ser assíncrono, temos que guardar o status da saga para devolver ao frontend. Se não a mensagem de erro se perde.
  private String status;
  private String erro;
  private String codigoErro;

  public SagaAutocadastroContexto() {
  }
  
  public SagaAutocadastroContexto(String idSaga, String cpfGerente, String idUsuario, String cpf, String email, String nome,
      String telefone, BigDecimal salario, String endereco, String cep, String cidade, String estado, String ativo,
      String status, String erro, String codigoErro) {
    this.idSaga = idSaga;
    this.cpfGerente = cpfGerente;
    this.idUsuario = idUsuario;
    this.cpf = cpf;
    this.email = email;
    this.nome = nome;
    this.telefone = telefone;
    this.salario = salario;
    this.endereco = endereco;
    this.cep = cep;
    this.cidade = cidade;
    this.estado = estado;
    this.ativo = ativo;
    this.status = status;
    this.erro = erro;
    this.codigoErro = codigoErro;
  }

  public String getIdSaga() {
    return idSaga;
  }

  public void setIdSaga(String idSaga) {
    this.idSaga = idSaga;
  }

  public String getCpfGerente() {
    return cpfGerente;
  }

  public void setCpfGerente(String cpfGerente) {
    this.cpfGerente = cpfGerente;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
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

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

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

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getAtivo() {
    return ativo;
  }

  public void setAtivo(String ativo) {
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
    return "SagaAutocadastroContexto [idSaga=" + idSaga + ", cpfGerente=" + cpfGerente + ", idUsuario=" + idUsuario + ", cpf=" + cpf
        + ", email=" + email + ", nome=" + nome + ", telefone=" + telefone + ", salario=" + salario + ", endereco="
        + endereco + ", cep=" + cep + ", cidade=" + cidade + ", estado=" + estado + ", ativo=" + ativo + ", status="
        + status + ", erro=" + erro + ", codigoErro=" + codigoErro + "]";
  }
}