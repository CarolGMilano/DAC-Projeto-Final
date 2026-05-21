package br.net.dac.msconta.model.dto;

import java.sql.Date;

public class OperacaoResponseDTO {
    public String conta;
    public Date data;
    public Double saldo;
    
    public OperacaoResponseDTO(String conta, Date data, Double saldo) {
        this.conta = conta;
        this.data = data;
        this.saldo = saldo;
    }
}

