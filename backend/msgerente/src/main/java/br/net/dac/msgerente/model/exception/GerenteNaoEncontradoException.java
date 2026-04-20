package br.net.dac.msgerente.model.exception;

public class GerenteNaoEncontradoException extends RuntimeException {
  public GerenteNaoEncontradoException() {
    super("Gerente não encontrado");
  }
}