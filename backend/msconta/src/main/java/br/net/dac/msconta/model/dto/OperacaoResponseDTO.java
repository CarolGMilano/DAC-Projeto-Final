package br.net.dac.msconta.model.dto;

import java.time.LocalDateTime;

public class OperacaoResponseDTO {
    public String conta;
    public LocalDateTime data;
    public Double saldo;
    
    public OperacaoResponseDTO(String conta, LocalDateTime data, Double saldo) {
        this.conta = conta;
        this.data = data;
        this.saldo = saldo;
    }
}

