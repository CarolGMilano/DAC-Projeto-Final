package com.br.net.dac.mscontaquery.model.mapper;


import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.MovimentacaoDTO;
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

    public static MovimentacaoDTO toMovimentacaoDTO(Movimentacao movimentacao) {
        MovimentacaoDTO DTO = new MovimentacaoDTO();
            DTO.data = movimentacao.getData();
            DTO.tipo = movimentacao.getTipo();
            DTO.origem = movimentacao.getOrigem();
            DTO.destino = movimentacao.getDestino();
            DTO.valor = movimentacao.getValor();

        return DTO;
    }
}