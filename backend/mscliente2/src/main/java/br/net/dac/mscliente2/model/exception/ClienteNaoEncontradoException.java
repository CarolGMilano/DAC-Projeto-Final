package br.net.dac.mscliente2.model.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException() {
    super("Cliente não encontrado");
  }
}