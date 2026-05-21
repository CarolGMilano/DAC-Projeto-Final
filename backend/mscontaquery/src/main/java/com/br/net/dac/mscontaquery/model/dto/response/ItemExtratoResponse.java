package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDateTime;

import com.br.net.dac.mscontaquery.model.MovimentacaoTipo;

public class ItemExtratoResponse {
    private LocalDateTime data;
    private MovimentacaoTipo tipo;
    private String origem;
    private String destino;
    private Double valor;

    public ItemExtratoResponse() {}

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public MovimentacaoTipo getTipo() {
        return tipo;
    }

    public void setTipo(MovimentacaoTipo tipo) {
        this.tipo = tipo;
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