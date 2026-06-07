package br.net.dac.msconta.model.exception;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException() {
        super("Saldo insuficiente");
    }
}
