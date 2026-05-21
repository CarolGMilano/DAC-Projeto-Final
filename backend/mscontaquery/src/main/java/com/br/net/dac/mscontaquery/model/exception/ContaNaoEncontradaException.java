package com.br.net.dac.mscontaquery.model.exception;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException() {
        super("Recurso não encontrado");
    }
}
