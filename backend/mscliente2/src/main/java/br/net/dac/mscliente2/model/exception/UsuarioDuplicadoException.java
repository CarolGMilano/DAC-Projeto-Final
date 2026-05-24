package br.net.dac.mscliente2.model.exception;

public class UsuarioDuplicadoException extends RuntimeException {
  public UsuarioDuplicadoException() {
    super("Usuário já cadastrado");
  }
}