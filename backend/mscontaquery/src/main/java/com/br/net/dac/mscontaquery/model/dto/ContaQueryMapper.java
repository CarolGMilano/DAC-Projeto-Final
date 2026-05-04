package com.br.net.dac.mscontaquery.model.dto;

import com.br.net.dac.mscontaquery.model.entity.Conta;

public class ContaQueryMapper {
    public static ContaQueryResponseDTO toDTO(Conta conta) {
        if (conta == null) return null;
        ContaQueryResponseDTO dto = new ContaQueryResponseDTO();
        dto.numeroConta = conta.getNumeroConta();
        dto.ativo = conta.isAtivo();
        dto.idCliente = conta.getIdCliente();
        dto.idGerente = conta.getIdGerente();
        dto.dataCriacao = conta.getDataCriacao();
        dto.saldo = conta.getSaldo();
        dto.limite = conta.getLimite();
        return dto;
    }
}
