package br.net.dac.mscliente2.model.exception;

public class CPFDuplicadoException extends RuntimeException {
  public CPFDuplicadoException() {
    super("CPF já cadastrado");
  }
}