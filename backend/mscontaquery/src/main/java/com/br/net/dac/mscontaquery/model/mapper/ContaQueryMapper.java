package com.br.net.dac.mscontaquery.model.mapper;

import java.util.stream.Collectors;

import com.br.net.dac.mscontaquery.model.dto.response.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.response.DashboardGerenteDTO;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoDTO;
import com.br.net.dac.mscontaquery.model.dto.response.MovimentacaoDTO;
import com.br.net.dac.mscontaquery.model.dto.response.RelatorioClientesDTO;
import com.br.net.dac.mscontaquery.model.dto.response.Top3ClientesDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.entity.Movimentacao;

public class ContaQueryMapper {

    public static ContaResponseDTO toDTO(Conta conta) {
        if (conta == null) {
            return null;
        }

        ContaResponseDTO dto = new ContaResponseDTO();

        dto.setNumeroConta(conta.getNumeroConta());

        dto.setCpfCliente(conta.getCpfCliente());
        dto.setNomeCliente(conta.getNomeCliente());

        dto.setSaldo(conta.getSaldo());
        dto.setLimite(conta.getLimite());
        dto.setSalario(conta.getSalario());

        dto.setIdGerente(conta.getIdGerente());
        dto.setNomeGerente(conta.getNomeGerente());

        dto.setStatusConta(conta.getStatusConta());
        dto.setDataCriacao(conta.getDataCriacao());

        return dto;
    }

    public static ExtratoDTO toExtratoDTO(Conta conta) {
        if (conta == null) {
            return null;
        }

        ExtratoDTO dto = new ExtratoDTO();

        dto.setNumeroConta(conta.getNumeroConta());
        dto.setNomeCliente(conta.getNomeCliente());
        dto.setSaldoAtual(conta.getSaldo());

        dto.setMovimentacoes(
                conta.getMovimentacoes()
                        .stream()
                        .map(ContaQueryMapper::toMovimentacaoDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static MovimentacaoDTO toMovimentacaoDTO(Movimentacao movimentacao) {
    if (movimentacao == null) {
        return null;
    }

    MovimentacaoDTO dto = new MovimentacaoDTO();

    dto.setTipo(movimentacao.getTipo());
    dto.setValor(movimentacao.getValor());
    dto.setDescricao(movimentacao.getDescricao());
    dto.setDataMovimentacao(movimentacao.getDataMovimentacao());

    return dto;
}

    public static Top3ClientesDTO toTop3ClientesDTO(Conta conta) {
        if (conta == null) {
            return null;
        }

        Top3ClientesDTO dto = new Top3ClientesDTO();

        dto.setNomeCliente(conta.getNomeCliente());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setSaldo(conta.getSaldo());
        dto.setLimite(conta.getLimite());
        dto.setNomeGerente(conta.getNomeGerente());

        return dto;
    }

    public static DashboardGerenteDTO toDashboardGerenteDTO(Conta conta) {
        if (conta == null) {
            return null;
        }

        DashboardGerenteDTO dto = new DashboardGerenteDTO();

        dto.setIdGerente(conta.getIdGerente());
        dto.setNomeGerente(conta.getNomeGerente());
        dto.setTotalClientes(1);
        dto.setTotalSaldoPositivo(conta.getSaldo() > 0 ? conta.getSaldo() : 0.0);
        dto.setTotalSaldoNegativo(conta.getSaldo() < 0 ? conta.getSaldo() : 0.0);
        dto.setTotalSaldoGeral(conta.getSaldo());

        return dto;
    }

    public static RelatorioClientesDTO toRelatorioClientesDTO(Conta conta) {
        if (conta == null) {
            return null;
        }

        RelatorioClientesDTO dto = new RelatorioClientesDTO();

        dto.setNomeCliente(conta.getNomeCliente());
        dto.setCpfCliente(conta.getCpfCliente());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setNomeGerente(conta.getNomeGerente());
        dto.setSaldo(conta.getSaldo());
        dto.setLimite(conta.getLimite());
        dto.setSalario(conta.getSalario());
        dto.setStatusConta(conta.getStatusConta());
        dto.setDataCriacao(conta.getDataCriacao());

        return dto;
    }

}