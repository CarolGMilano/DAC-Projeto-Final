package com.br.net.dac.mscontaquery.model.dto.response;

import java.time.LocalDateTime;

public class MovimentacaoDTO {

    private String tipo;
    private Double valor;
    private String descricao;
    private LocalDateTime dataMovimentacao;

    public MovimentacaoDTO() {
    }

    public MovimentacaoDTO(String tipo, Double valor, String descricao, LocalDateTime dataMovimentacao) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}