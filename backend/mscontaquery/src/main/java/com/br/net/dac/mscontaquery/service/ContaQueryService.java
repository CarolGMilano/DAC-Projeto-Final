package com.br.net.dac.mscontaquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.ExtratoResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.SaldoResponseDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.entity.Movimentacao;
import com.br.net.dac.mscontaquery.model.mapper.ContaQueryMapper;
import com.br.net.dac.mscontaquery.repository.ContaRepository;
import com.br.net.dac.mscontaquery.repository.MovimentacaoRepository;

@Service
public class ContaQueryService {

    @Autowired
    private ContaRepository contaRepository;
    private MovimentacaoRepository movimentacaoRepository;

    public List<ContaResponseDTO> buscarContas() {
        return contaRepository.findAll().stream()
                .map(ContaQueryMapper::toDTO)
                .toList();
    }

    public String buscarGerenteDisponivel() {
        return contaRepository.findGerenteWithLeastActiveContas()
                .orElseThrow(() -> new RuntimeException("Nenhum gerente disponível"));
    }

    public SaldoResponseDTO consultarSaldo(String numero) {
        Conta conta = contaRepository.findById(numero)
        .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

    return ContaQueryMapper.toSaldoDTO(conta);
    }

    public ExtratoResponseDTO consultarExtrato(String numero) {
        Conta conta = contaRepository.findById(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

        List<Movimentacao> movimentacoes = movimentacaoRepository.findByOrigemOrDestinoOrderByDataAsc(numero, numero);

        ExtratoResponseDTO extrato = new ExtratoResponseDTO();
        extrato.conta = conta.getNumero();
        extrato.saldo = conta.getSaldo();
        extrato.movimentacoes = movimentacoes.stream()
                .map(ContaQueryMapper::toMovimentacaoDTO)
                .toList();

        return extrato;
    }

    //update, delete e create do command

}