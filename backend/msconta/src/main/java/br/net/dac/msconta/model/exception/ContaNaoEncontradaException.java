package br.net.dac.msconta.model.exception;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException() {
        super("Recurso não encontrado");
    }
}
