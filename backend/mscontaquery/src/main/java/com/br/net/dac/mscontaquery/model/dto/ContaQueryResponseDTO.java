package com.br.net.dac.mscontaquery.model.dto;

import java.sql.Date;

public class ContaQueryResponseDTO {
    public boolean ativo;
    public Long idGerente;
    public Long idCliente;
    public String numeroConta;
    public Date dataCriacao;
    public double saldo;
    public double limite;
}