package br.net.dac.msgerente.model.exception;

public class UsuarioDuplicadoException extends RuntimeException {
  public UsuarioDuplicadoException() {
    super("Usuário já cadastrado");
  }
}