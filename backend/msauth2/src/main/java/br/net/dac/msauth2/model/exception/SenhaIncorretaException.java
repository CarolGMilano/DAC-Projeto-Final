package br.net.dac.msauth2.model.exception;

public class SenhaIncorretaException extends RuntimeException {
  public SenhaIncorretaException() {
    super("Senha incorreta");
  }
}