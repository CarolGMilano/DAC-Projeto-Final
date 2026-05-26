package com.br.net.dac.mscontaquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.ExtratoResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.MovimentacaoRequestDTO;
import com.br.net.dac.mscontaquery.model.dto.MovimentacaoResponseDTO;
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

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    // Métodos de contas

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

    // Métodos de Movimentacao

    public MovimentacaoResponseDTO criarMovimentacao (MovimentacaoRequestDTO requestDTO) {
        
        // Busca no BD da query se existe aconta com o número
        // Conta conta =   contaRepository.findById(requestDTO.getDestino())
        //                 .orElseThrow(() -> new RuntimeException("Conta não encontrada " + requestDTO.findById));
        
        // Primeiro: Registrar a alteração na conta
        // Buscar a conta do cliente pelo seu CPF
        Conta contaAlterar = contaRepository.findTop1ByClienteCpf(requestDTO.getDestino());
        // Altera o saldo e salva na conta
        contaAlterar.setSaldo(contaAlterar.getSaldo() + requestDTO.getValor());
        contaRepository.save(contaAlterar);
        
        // Segundo: Registrar a movimentação na conta
        // Criação da movimentação a ser registrada no BD, obtendo os dados do DTO
        Movimentacao movimentacao = new Movimentacao();
            movimentacao.setId(requestDTO.getId());
            movimentacao.setData(requestDTO.getData());
            movimentacao.setTipo(requestDTO.getTipo());
            movimentacao.setOrigem(requestDTO.getOrigem());
            movimentacao.setDestino(requestDTO.getDestino());
            movimentacao.setValor(requestDTO.getValor());


            // Salva na tabela movimentação
            Movimentacao movimentacaoAdicionada = movimentacaoRepository.save(movimentacao);

            return new MovimentacaoResponseDTO(
                movimentacao.getId(),
                movimentacaoAdicionada.getTipo(),                
                movimentacaoAdicionada.getValor(),
                movimentacaoAdicionada.getData(),
                movimentacaoAdicionada.getOrigem(),
                movimentacaoAdicionada.getDestino()
            );

    }
}