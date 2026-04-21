package br.net.dac.msconta.model.exception;

public class ContaInativaException extends RuntimeException{
    public ContaInativaException() {
        super("Recurso já está inativo");
    }
}
