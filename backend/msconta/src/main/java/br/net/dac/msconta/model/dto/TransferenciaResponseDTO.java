package br.net.dac.msconta.model.dto;

import java.time.LocalDateTime;

public class TransferenciaResponseDTO {
    public String conta;
    public LocalDateTime data;
    public String destino;
    public Double saldo;
    public Double valor;
    public TransferenciaResponseDTO(String conta, LocalDateTime data, String destino, Double saldo, Double valor) {
        this.conta = conta;
        this.data = data;
        this.destino = destino;
        this.saldo = saldo;
        this.valor = valor;
    }
}

