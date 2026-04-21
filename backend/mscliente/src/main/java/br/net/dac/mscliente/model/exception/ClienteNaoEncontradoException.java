package br.net.dac.mscliente.model.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException() {
    super("Cliente não encontrado");
  }
}