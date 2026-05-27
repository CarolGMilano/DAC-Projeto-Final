package com.br.net.dac.mscontaquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.ContaResumoDTO;
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

    public ContaResumoDTO validarClienteExiste(String cpf) {
        Conta conta = contaRepository.findByCpfCliente(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para o CPF: " + cpf));

        return ContaQueryMapper.toResumoDTO(conta);
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
        
        switch (requestDTO.getTipo()) {
            case "SAQUE":
                // Retira do saldo da conta
                Conta contaSacar = contaRepository.findTop1ByClienteCpf(requestDTO.getDestino());
                contaSacar.setSaldo(contaSacar.getSaldo() - requestDTO.getValor());
                contaRepository.save(contaSacar);

                // Criação da movimentação a ser registrada
                Movimentacao movimentacaoSaque = new Movimentacao();
                movimentacaoSaque.setId(requestDTO.getId());
                movimentacaoSaque.setData(requestDTO.getData());
                movimentacaoSaque.setTipo(requestDTO.getTipo());
                movimentacaoSaque.setOrigem(requestDTO.getOrigem());
                movimentacaoSaque.setDestino(requestDTO.getDestino());
                movimentacaoSaque.setValor(requestDTO.getValor());

                // Salva na tabela movimentação
                Movimentacao movimentacaoSaqueSalva = movimentacaoRepository.save(movimentacaoSaque);
                return new MovimentacaoResponseDTO(
                movimentacaoSaqueSalva.getId(),
                movimentacaoSaqueSalva.getTipo(),                
                movimentacaoSaqueSalva.getValor(),
                movimentacaoSaqueSalva.getData(),
                movimentacaoSaqueSalva.getOrigem(),
                movimentacaoSaqueSalva.getDestino()
            );
                
            case "DEPOSITO":
                // Deposita no saldo da conta
                Conta contaDepositar = contaRepository.findTop1ByClienteCpf(requestDTO.getDestino());
                contaDepositar.setSaldo(contaDepositar.getSaldo() + requestDTO.getValor());
                contaRepository.save(contaDepositar);
                
                // Criação da movimentação a ser registrada
                Movimentacao movimentacaoDeposito = new Movimentacao();
                movimentacaoDeposito.setId(requestDTO.getId());
                movimentacaoDeposito.setData(requestDTO.getData());
                movimentacaoDeposito.setTipo(requestDTO.getTipo());
                movimentacaoDeposito.setOrigem(requestDTO.getOrigem());
                movimentacaoDeposito.setDestino(requestDTO.getDestino());
                movimentacaoDeposito.setValor(requestDTO.getValor());

                // Salva na tabela movimentação
                Movimentacao movimentacaoDepositoSalva = movimentacaoRepository.save(movimentacaoDeposito);
                return new MovimentacaoResponseDTO(
                movimentacaoDepositoSalva.getId(),
                movimentacaoDepositoSalva.getTipo(),                
                movimentacaoDepositoSalva.getValor(),
                movimentacaoDepositoSalva.getData(),
                movimentacaoDepositoSalva.getOrigem(),
                movimentacaoDepositoSalva.getDestino()
            );

            case "TRANSFERENCIA":
                // Deposita no saldo da conta destino
                Conta contaTransferenciaDestino = contaRepository.findTop1ByClienteCpf(requestDTO.getDestino());
                contaTransferenciaDestino.setSaldo(contaTransferenciaDestino.getSaldo() + requestDTO.getValor());
                contaRepository.save(contaTransferenciaDestino);
                
                // Retira no saldo da conta origem
                Conta contaTransferenciaOrigem = contaRepository.findTop1ByClienteCpf(requestDTO.getOrigem());
                contaTransferenciaOrigem.setSaldo(contaTransferenciaOrigem.getSaldo() - requestDTO.getValor());
                contaRepository.save(contaTransferenciaOrigem);

                // Salva a movimentação
                Movimentacao movimentacaoTransferencia = new Movimentacao();
                movimentacaoTransferencia.setId(requestDTO.getId());
                movimentacaoTransferencia.setData(requestDTO.getData());
                movimentacaoTransferencia.setTipo(requestDTO.getTipo());
                movimentacaoTransferencia.setOrigem(requestDTO.getOrigem());
                movimentacaoTransferencia.setDestino(requestDTO.getDestino());
                movimentacaoTransferencia.setValor(requestDTO.getValor());

                // Salva na tabela movimentação
                Movimentacao movimentacaoTransferenciaSalva = movimentacaoRepository.save(movimentacaoTransferencia);
                return new MovimentacaoResponseDTO(
                movimentacaoTransferenciaSalva.getId(),
                movimentacaoTransferenciaSalva.getTipo(),                
                movimentacaoTransferenciaSalva.getValor(),
                movimentacaoTransferenciaSalva.getData(),
                movimentacaoTransferenciaSalva.getOrigem(),
                movimentacaoTransferenciaSalva.getDestino()
                );
                
            default:
                return new MovimentacaoResponseDTO(            
            );
        }

        // // Primeiro: Registrar a alteração na conta
        // // Buscar a conta do cliente pelo seu CPF
        // Conta contaAlterar = contaRepository.findTop1ByClienteCpf(requestDTO.getDestino());
        // // Altera o saldo e salva na conta
        // contaAlterar.setSaldo(contaAlterar.getSaldo() + requestDTO.getValor());
        // contaRepository.save(contaAlterar);
        
        // // Segundo: Registrar a movimentação na conta
        // // Criação da movimentação a ser registrada no BD, obtendo os dados do DTO
        // Movimentacao movimentacao = new Movimentacao();
        //     movimentacao.setId(requestDTO.getId());
        //     movimentacao.setData(requestDTO.getData());
        //     movimentacao.setTipo(requestDTO.getTipo());
        //     movimentacao.setOrigem(requestDTO.getOrigem());
        //     movimentacao.setDestino(requestDTO.getDestino());
        //     movimentacao.setValor(requestDTO.getValor());


        //     // Salva na tabela movimentação
        //     Movimentacao movimentacaoAdicionada = movimentacaoRepository.save(movimentacao);

        //     return new MovimentacaoResponseDTO(
        //         movimentacao.getId(),
        //         movimentacaoAdicionada.getTipo(),                
        //         movimentacaoAdicionada.getValor(),
        //         movimentacaoAdicionada.getData(),
        //         movimentacaoAdicionada.getOrigem(),
        //         movimentacaoAdicionada.getDestino()
        //     );

    }
}