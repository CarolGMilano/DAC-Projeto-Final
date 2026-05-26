package com.br.net.dac.mscontaquery.model.mapper;


import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.MovimentacaoResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.SaldoResponseDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.entity.Movimentacao;

public class ContaQueryMapper {

    public static ContaResponseDTO toDTO(Conta conta) {
        ContaResponseDTO DTO = new ContaResponseDTO();
            DTO.numero = conta.getNumero();
            DTO.saldo = conta.getSaldo();
            DTO.limite = conta.getLimite();
            DTO.gerenteCpf = conta.getGerenteCpf();
            DTO.clienteCpf = conta.getClienteCpf();
            DTO.data = conta.getData();
        return DTO;
    }

    public static SaldoResponseDTO toSaldoDTO(Conta conta) {
        SaldoResponseDTO DTO = new SaldoResponseDTO();
            DTO.conta = conta.getNumero();
            DTO.saldo = conta.getSaldo();
            DTO.cliente = conta.getClienteCpf();
        return DTO;
    }

    public static MovimentacaoResponseDTO toMovimentacaoDTO(Movimentacao movimentacao) {
        MovimentacaoResponseDTO DTO = new MovimentacaoResponseDTO();
            DTO.setData(movimentacao.getData());
            DTO.setTipo(movimentacao.getTipo());
            DTO.setOrigem( movimentacao.getOrigem());
            DTO.setDestino(movimentacao.getDestino());
            DTO.setValor(movimentacao.getValor());

        return DTO;
    }
}