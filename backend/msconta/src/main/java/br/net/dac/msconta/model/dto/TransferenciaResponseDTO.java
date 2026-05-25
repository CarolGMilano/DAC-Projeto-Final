package br.net.dac.msconta.model.dto;

import java.sql.Date;

public class TransferenciaResponseDTO {
    public String conta;
    public Date data;
    public String destino;
    public Double saldo;
    public Double valor;
    public TransferenciaResponseDTO(String conta, Date data, String destino, Double saldo, Double valor) {
        this.conta = conta;
        this.data = data;
        this.destino = destino;
        this.saldo = saldo;
        this.valor = valor;
    }
}

