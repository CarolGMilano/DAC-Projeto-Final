package com.br.net.dac.mscontaquery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.response.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.response.DashboardGerenteDTO;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoDTO;
import com.br.net.dac.mscontaquery.model.dto.response.RelatorioClientesDTO;
import com.br.net.dac.mscontaquery.model.dto.response.Top3ClientesDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.exception.ContaNaoEncontradaException;
import com.br.net.dac.mscontaquery.model.mapper.ContaQueryMapper;
import com.br.net.dac.mscontaquery.repository.ContaQueryRepository;

@Service
public class ContaQueryService {

    @Autowired
    private ContaQueryRepository repository;

    public ContaResponseDTO buscarContaPorCliente(String cpf) {
        Conta conta = repository.findByCpfCliente(cpf);

        if (conta == null) {
            throw new ContaNaoEncontradaException();
        }

        return ContaQueryMapper.toDTO(conta);
    }

    public ContaResponseDTO buscarContaPorNumero(String numero) {
        Conta conta = repository.findByNumeroConta(numero);

        if (conta == null) {
            throw new ContaNaoEncontradaException();
        }

        return ContaQueryMapper.toDTO(conta);
    }

    public Double consultarSaldo(String id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException());

        return conta.getSaldo();
    }

    public ExtratoDTO consultarExtrato(String id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException());

        return ContaQueryMapper.toExtratoDTO(conta);
    }

    public List<ContaResponseDTO> listarClientesDoGerente(Long idGerente) {
        return repository.findByIdGerente(idGerente)
                .stream()
                .map(ContaQueryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContaResponseDTO buscarDadosClienteConta(String cpf) {
        Conta conta = repository.findByCpfCliente(cpf);

        if (conta == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        return ContaQueryMapper.toDTO(conta);
    }

    public List<Top3ClientesDTO> listarTop3ClientesPorSaldo() {
        return repository.findTop3ByOrderBySaldoDesc()
                .stream()
                .map(ContaQueryMapper::toTop3ClientesDTO)
                .collect(Collectors.toList());
    }

    public List<DashboardGerenteDTO> gerarDashboardGerentes() {
        return repository.findAll()
                .stream()
                .map(ContaQueryMapper::toDashboardGerenteDTO)
                .collect(Collectors.toList());
    }

    public List<RelatorioClientesDTO> gerarRelatorioClientes() {
        return repository.findAll()
                .stream()
                .map(ContaQueryMapper::toRelatorioClientesDTO)
                .collect(Collectors.toList());
    }
}