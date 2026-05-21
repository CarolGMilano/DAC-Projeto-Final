package br.net.dac.msauth2.model.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
  public UsuarioNaoEncontradoException() {
    super("Usuário não encontrado");
  }
}
