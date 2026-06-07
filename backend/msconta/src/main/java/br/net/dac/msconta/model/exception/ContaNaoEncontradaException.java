package br.net.dac.msconta.model.exception;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException(String conta) {
        super("A conta " + conta +  " não foi encontrada");
    }
}
