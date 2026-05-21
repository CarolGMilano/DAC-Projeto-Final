package com.br.net.dac.mscontaquery.model.entity;

import java.time.LocalDateTime;

import com.br.net.dac.mscontaquery.model.MovimentacaoTipo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Movimentacao {

    private String id;

    private MovimentacaoTipo tipo; 
    // DEPOSITO, SAQUE, TRANSFERENCIA_ENTRADA, TRANSFERENCIA_SAIDA

    private Double valor;

    private String origem;
    private String destino;


    private LocalDateTime data;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public MovimentacaoTipo getTipo() {
        return tipo;
    }


    public void setTipo(MovimentacaoTipo tipo) {
        this.tipo = tipo;
    }


    public Double getValor() {
        return valor;
    }


    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getOrigem() {
        return origem;
    }


    public void setOrigem(String origem) {
        this.origem = origem;
    }


    public String getDestino() {
        return destino;
    }


    public void setDestino(String destino) {
        this.destino = destino;
    }


    public LocalDateTime getData() {
        return data;
    }


    public void setData(LocalDateTime data) {
        this.data = data;
    }

}