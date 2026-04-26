package br.net.dac.msgerente.model.exception;

public class CPFDuplicadoException extends RuntimeException {
  public CPFDuplicadoException() {
    super("CPF já cadastrado");
  }
}