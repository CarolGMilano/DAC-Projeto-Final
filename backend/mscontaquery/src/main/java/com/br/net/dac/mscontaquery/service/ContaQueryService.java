package com.br.net.dac.mscontaquery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.response.ConsultaDeClientesDTO;
import com.br.net.dac.mscontaquery.model.dto.response.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.response.DashboardAdminDTO;
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

    public ContaResponseDTO buscarContaPorCpfCliente(String cpf) {
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

    public ExtratoDTO consultarExtrato(String numeroConta) {
        Conta conta = repository.findByNumeroConta(numeroConta);

        return ContaQueryMapper.toExtratoDTO(conta);
    }

    public List<ConsultaDeClientesDTO> listarClientesDoGerente(Long idGerente) {
        return repository.findByIdGerente(idGerente)
                .stream()
                .map(ContaQueryMapper::toConsultaDeClientesDTO)
                .collect(Collectors.toList());
    }

    public List<Top3ClientesDTO> listarTop3ClientesPorSaldo() {
        return repository.findTop3ByOrderBySaldoDesc()
                .stream()
                .map(ContaQueryMapper::toTop3ClientesDTO)
                .collect(Collectors.toList());
    }

    public List<DashboardGerenteDTO> gerarDashboardDoGerente() {
        return repository.findByStatusConta("PENDENTE")
                .stream()
                .map(ContaQueryMapper::toDashboardGerenteDTO)
                .collect(Collectors.toList());
    }
    
    public List<DashboardAdminDTO> gerarDashboardDoAdmin() {
        return repository.gerarDashboardAdmin();
    }

    public List<RelatorioClientesDTO> gerarRelatorioClientes() {
        return repository.findAllByOrderByNomeClienteAsc()
                .stream()
                .map(ContaQueryMapper::toRelatorioClientesDTO)
                .collect(Collectors.toList());
    }
}