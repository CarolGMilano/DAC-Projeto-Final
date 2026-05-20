package com.br.net.dac.mscontaquery.model.mapper;

import java.util.List;

import com.br.net.dac.mscontaquery.model.dto.response.ClienteParaAprovarResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ClienteResponse;
import com.br.net.dac.mscontaquery.model.dto.response.DadoConta;
import com.br.net.dac.mscontaquery.model.dto.response.DadoGerente;
import com.br.net.dac.mscontaquery.model.dto.response.DadosClienteResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ItemExtratoResponse;
import com.br.net.dac.mscontaquery.model.dto.response.SaldoResponse;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.entity.Gerente;
import com.br.net.dac.mscontaquery.model.entity.Movimentacao;

public class ContaQueryMapper {

    public static ClienteResponse toClienteResponse(Conta conta) {
        if (conta == null) {
            return null;
        }

        ClienteResponse response = new ClienteResponse();

        response.setCpf(conta.getCliente().getCpf());
        response.setNome(conta.getCliente().getNome());
        response.setEmail(conta.getCliente().getEmail());
        response.setTelefone(conta.getCliente().getTelefone());
        response.setEndereco(conta.getCliente().getEndereco());
        response.setCidade(conta.getCliente().getCidade());
        response.setEstado(conta.getCliente().getEstado());
        response.setConta(conta.getNumero());
        response.setSaldo(conta.getSaldo());
        response.setLimite(conta.getLimite());

        return response;
    }

    public static DadosClienteResponse toDadosClienteResponse(Conta conta) {
        if (conta == null) {
            return null;
        }

        DadosClienteResponse itemRelatorio = new DadosClienteResponse();

        itemRelatorio.setCpf(conta.getCliente().getCpf());
        itemRelatorio.setNome(conta.getCliente().getNome());
        itemRelatorio.setTelefone(conta.getCliente().getTelefone());
        itemRelatorio.setEmail(conta.getCliente().getEmail());
        itemRelatorio.setEndereco(conta.getCliente().getEndereco());
        itemRelatorio.setCidade(conta.getCliente().getCidade());
        itemRelatorio.setEstado(conta.getCliente().getEstado());
        itemRelatorio.setSalario(conta.getSalario());
        itemRelatorio.setConta(conta.getNumero());
        itemRelatorio.setSaldo(conta.getSaldo());
        itemRelatorio.setLimite(conta.getLimite());
        itemRelatorio.setGerente(conta.getGerente().getCpf());
        itemRelatorio.setGerenteNome(conta.getGerente().getNome());
        itemRelatorio.setGerenteEmail(conta.getGerente().getEmail());

        return itemRelatorio;
    }

    public static SaldoResponse toSaldoResponse(Conta conta) {
        if (conta == null) {
            return null;
        }

        SaldoResponse response = new SaldoResponse();

        response.setCliente(conta.getCliente().getCpf());
        response.setConta(conta.getNumero());
        response.setSaldo(conta.getSaldo());

        return response;
    }

    public static ExtratoResponse toExtratoResponse(Conta conta) {
    if (conta == null) {
        return null;
    }

    ExtratoResponse response = new ExtratoResponse();

    response.setConta(conta.getNumero());
    response.setSaldo(conta.getSaldo());

    if (conta.getMovimentacoes() != null) {

        List<ItemExtratoResponse> movimentacoes =
            conta.getMovimentacoes()
                .stream()
                .map(ContaQueryMapper::toItemExtratoResponse)
                .toList();

        response.setMovimentacoes(movimentacoes);
    }

    return response;
    }

    public static ItemExtratoResponse toItemExtratoResponse(
            Movimentacao movimentacao
    ) {
        if (movimentacao == null) {
            return null;
        }

        ItemExtratoResponse item = new ItemExtratoResponse();

        item.setDestino(movimentacao.getDestino());
        item.setOrigem(movimentacao.getOrigem());
        item.setData(movimentacao.getData());
        item.setTipo(movimentacao.getTipo());
        item.setValor(movimentacao.getValor());

        return item;
    }

    public static ClienteParaAprovarResponse toClienteParaAprovarResponse(Conta conta) {

    if (conta == null || conta.getCliente() == null) {
        return null;
    }

    ClienteParaAprovarResponse response = new ClienteParaAprovarResponse();

    response.setCpf(conta.getCliente().getCpf());
    response.setNome(conta.getCliente().getNome());
    response.setEmail(conta.getCliente().getEmail());
    response.setSalario(conta.getSalario());
    response.setEndereco(conta.getCliente().getEndereco());
    response.setCidade(conta.getCliente().getCidade());
    response.setEstado(conta.getCliente().getEstado());

    return response;
}

public static DadoGerente toDadoGerente(Gerente gerente) {
    if (gerente == null) return null;

    DadoGerente dado = new DadoGerente();
    dado.setCpf(gerente.getCpf());
    dado.setNome(gerente.getNome());
    dado.setEmail(gerente.getEmail());
    dado.setTipo(gerente.getTipo());

    return dado;
}

public static DadoConta toDadoConta(Conta conta) {
    if (conta == null) return null;

    DadoConta dado = new DadoConta();
    dado.setCliente(conta.getCliente().getCpf());
    dado.setNumero(conta.getNumero());
    dado.setSaldo(conta.getSaldo());
    dado.setLimite(conta.getLimite());
    dado.setGerente(conta.getGerente().getCpf());
    dado.setCriacao(conta.getDataCriacao());

    return dado;
}


}