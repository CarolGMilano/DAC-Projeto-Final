package br.net.dac.msconta.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.dto.OperacaoResponseDTO;
import br.net.dac.msconta.model.dto.TransferenciaRequestDTO;
import br.net.dac.msconta.model.dto.TransferenciaResponseDTO;
import br.net.dac.msconta.model.entity.Conta;
import br.net.dac.msconta.model.entity.Movimentacao;
// import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
import br.net.dac.msconta.repository.ContaRepository;
import br.net.dac.msconta.repository.MovimentacaoRepository;

@Service
public class ContaCommandService {

    @Autowired
    private ContaRepository contaRepository;
    private MovimentacaoRepository movimentacaoRepository;
/* 
    // 1. MÉTODOS
    // 1.1 VALIDAR SE CONTA É VALIDA
    private void validaConta(ContaRequestDTO conta) {
        if (conta.getIdCliente() == null) throw new IllegalArgumentException("MsConta: Id do cliente == null");
        if (conta.getIdGerente() == null) throw new IllegalArgumentException("MsConta: Id do gerente == null");
        if (conta.isAtivo() == false) throw new IllegalArgumentException("Conta inativa");        
    }

    // 1.2 Criar numero conta aleatório de 4 dígitos
    private String geraNumeroConta() {
        Random random = new Random();
        String novoNumeroConta;

        do {
            int numero = random.nextInt(10000);
            novoNumeroConta = String.format("%04d", numero);
        } while (contaRepository.existsByNumeroConta(novoNumeroConta));
        return novoNumeroConta;
    }

    
    // 1.3 CRIA CONTA (CREATE/POST)
    public ContaResponseDTO criarConta(ContaRequestDTO requestDTO) {
        validaConta(requestDTO);

        // VALIDAÇÃO DE EXISTÊNCIA DE CONTA
        // NÃO SEI SE VAMOS USAR PRA VERIFICAR SE CONTA É ATIVA OU NÃO, ENTÃO DEIXAMOS COMENTADO CASO PRECISE
        // Conta contaEncontrado = contaRepository.findById(contaDTO.getId()).orElseThrow(ContaNaoEncontradaException::new);

        // if (!contaEncontrado.getAtivo()) {
        //     throw new ContaNaoEncontradaException();
        // }
        
        
        Conta conta = new Conta();
        conta.setNumeroConta(geraNumeroConta());
        conta.setAtivo(true);
        conta.setIdGerente(requestDTO.getIdGerente());
        conta.setIdCliente(requestDTO.getIdCliente());
        conta.setSaldo(requestDTO.getSaldo());
        conta.setLimite(requestDTO.getLimite());
        
        Conta contaAdicionada = contaRepository.save(conta);

        return new ContaResponseDTO(
            contaAdicionada.isAtivo(),
            contaAdicionada.getIdGerente(),
            contaAdicionada.getIdCliente(),
            contaAdicionada.getNumeroConta(),
            contaAdicionada.getDataCriacao(),
            contaAdicionada.getSaldo(),
            contaAdicionada.getLimite()
        );        
    }

    // 1.4 ATUALIZAR CONTA (UPDATE/PUT)
    // BOTAR 
    public ContaResponseDTO atualizarConta(String numeroConta, ContaRequestDTO requestDTO) {
        validaConta(requestDTO);
        
        Conta contaEncontrada = contaRepository.findByNumeroConta(numeroConta);

        if (contaEncontrada == null || contaEncontrada.isAtivo() == false) {
             throw new ContaNaoEncontradaException();
         }

        Conta conta = new Conta(
            requestDTO.isAtivo(),
            requestDTO.getIdGerente(),
            requestDTO.getIdCliente(),
            contaEncontrada.getNumeroConta(),
            contaEncontrada.getDataCriacao(),
            requestDTO.getSaldo(),
            requestDTO.getLimite()
        ); 

        Conta contaAtualizada = contaRepository.save(conta);

        return new ContaResponseDTO(
            contaAtualizada.isAtivo(),
            contaAtualizada.getIdGerente(),
            contaAtualizada.getIdCliente(),
            contaAtualizada.getNumeroConta(),
            contaAtualizada.getDataCriacao(),
            contaAtualizada.getSaldo(),
            contaAtualizada.getLimite()
        );
    }

    // 1.5 DELETAR CONTA (DELETE)
    public void desativarConta (String numeroConta) {
        
        // VALIDAÇÃO
            // BUSCA CONTA SE EXISTE
                Conta contaEncontrada = contaRepository.findByNumeroConta(numeroConta);

                // CHECK SE ENCONTRADO
                if (contaEncontrada == null) {
                    throw new ContaNaoEncontradaException();
                }

            
            // CHECK SE INATIVO
                if(!contaEncontrada.isAtivo()) throw new ContaInativaException();
          
        // DESATIVA CONTA
        contaEncontrada.setAtivo(false);
        contaRepository.save(contaEncontrada);
}
*/

    

    public TransferenciaResponseDTO transferir(String numero, TransferenciaRequestDTO dto) {
        Conta origem = contaRepository.findById(numero)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));
        Conta destino = contaRepository.findById(dto.getDestino())
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + dto.getDestino()));

        if (origem.getSaldo() < dto.getValor()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo() - dto.getValor());
        destino.setSaldo(destino.getSaldo() + dto.getValor());

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setOrigem(origem);
        movimentacao.setDestino(destino);
        movimentacao.setValor(dto.getValor());
        movimentacao.setData(new Date(System.currentTimeMillis()));
        movimentacao.setTipo("TRANSFERENCIA");

        contaRepository.save(destino);
        contaRepository.save(origem);

        movimentacaoRepository.save(movimentacao);

        return new TransferenciaResponseDTO(
            movimentacao.getOrigem().getNumero(), 
            movimentacao.getData(), 
            movimentacao.getDestino().getNumero(), 
            origem.getSaldo(), 
            movimentacao.getValor());
    }

    public OperacaoResponseDTO depositar(String numero, Double valor) {
        Conta conta = contaRepository.findById(numero)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

        conta.setSaldo(conta.getSaldo() + valor);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setOrigem(conta);
        movimentacao.setDestino(null);
        movimentacao.setValor(valor);
        movimentacao.setData(new Date(System.currentTimeMillis()));
        movimentacao.setTipo("DEPOSITO");

        contaRepository.save(conta);

        movimentacaoRepository.save(movimentacao);

        return new OperacaoResponseDTO(
            movimentacao.getOrigem().getNumero(), 
            movimentacao.getData(), 
            conta.getSaldo());
    }
    public OperacaoResponseDTO sacar(String numero, Double valor) {
        Conta conta = contaRepository.findById(numero)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

        if (conta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo() - valor);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setOrigem(conta);
        movimentacao.setDestino(null);
        movimentacao.setValor(valor);
        movimentacao.setData(new Date(System.currentTimeMillis()));
        movimentacao.setTipo("SAQUE");

        contaRepository.save(conta);

        movimentacaoRepository.save(movimentacao);    

        return new OperacaoResponseDTO(
            movimentacao.getOrigem().getNumero(), 
            movimentacao.getData(), 
            conta.getSaldo());
    }

}