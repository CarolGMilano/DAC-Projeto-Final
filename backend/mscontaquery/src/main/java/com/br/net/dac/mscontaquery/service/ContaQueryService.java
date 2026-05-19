package com.br.net.dac.mscontaquery.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.response.SaldoResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ClienteParaAprovarResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ClienteResponse;
import com.br.net.dac.mscontaquery.model.dto.response.DadosClienteResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ItemDashboardResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoResponse;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.exception.ContaNaoEncontradaException;
import com.br.net.dac.mscontaquery.model.mapper.ContaQueryMapper;
import com.br.net.dac.mscontaquery.repository.ContaQueryRepository;

@Service
public class ContaQueryService {

    @Autowired
    private ContaQueryRepository repository;

    public DadosClienteResponse buscarContaPorCpfCliente(String cpf) {
        Conta conta = repository.findByCpfClienteAndStatus(cpf, "ATIVA");
        if (conta == null) throw new ContaNaoEncontradaException();
        return ContaQueryMapper.toDadosClienteResponse(conta);

    }


    public SaldoResponse consultarSaldo(String numeroConta) {
        Conta conta = repository.findByNumeroAndStatus(numeroConta, "ATIVA");
        if (conta == null) throw new ContaNaoEncontradaException();
        return ContaQueryMapper.toSaldoResponse(conta);
    }

    public ExtratoResponse consultarExtrato(String numeroConta) {
        Conta conta = repository.findByNumeroAndStatus(numeroConta, "ATIVA");
        return ContaQueryMapper.toExtratoResponse(conta);

    }

    public List<ClienteResponse> listarClientes() {
        return repository.findAllByStatusOrderByClienteNomeAsc("ATIVA")
            .stream()
            .map(ContaQueryMapper::toClienteResponse)
            .toList();
    }

    public List<ClienteResponse> listarTop3ClientesPorSaldo() {
        return repository.findTop3ByStatusOrderBySaldoDesc("ATIVA")
            .stream()
            .map(ContaQueryMapper::toClienteResponse)
            .toList();
    }

    public List<ItemDashboardResponse> gerarDashboardDoAdmin() {
        List<Conta> contas = repository.findByStatus("ATIVA");

        Map<String, List<Conta>> contasPorGerente = contas.stream()
                .collect(Collectors.groupingBy(c -> c.getGerente().getCpf()));

        return contasPorGerente.entrySet().stream()
                .map(entry -> {
                    List<Conta> contasDoGerente = entry.getValue();

                    ItemDashboardResponse item = new ItemDashboardResponse();
                    item.setGerente(ContaQueryMapper.toDadoGerente(contasDoGerente.get(0).getGerente()));
                    item.setClientes(contasDoGerente.stream()
                            .map(ContaQueryMapper::toDadoConta)
                            .toList());

                    double saldoPositivo = contasDoGerente.stream()
                            .mapToDouble(Conta::getSaldo)
                            .filter(s -> s > 0)
                            .sum();

                    double saldoNegativo = contasDoGerente.stream()
                            .mapToDouble(Conta::getSaldo)
                            .filter(s -> s < 0)
                            .sum();

                    item.setSaldoPositivo(saldoPositivo);
                    item.setSaldoNegativo(saldoNegativo);

                    return item;
                })
                .toList();
        }
    
        public List<ClienteParaAprovarResponse> gerarDashboardDoGerente() {
            return repository.findByStatus("PENDENTE")
                    .stream()
                    .map(ContaQueryMapper::toClienteParaAprovarResponse)
                    .toList();
        }

        public List<DadosClienteResponse> gerarRelatorioClientes() {
            return repository.findAllByStatusOrderByClienteNomeAsc("ATIVA")
                    .stream()
                    .map(ContaQueryMapper::toDadosClienteResponse)
                    .toList();
        }
}