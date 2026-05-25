package br.net.dac.msconta.service;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.dto.ContaRequestDTO;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.dto.OperacaoResponseDTO;
import br.net.dac.msconta.model.dto.TransferenciaRequestDTO;
import br.net.dac.msconta.model.dto.TransferenciaResponseDTO;
import br.net.dac.msconta.model.dto.ValorDTO;
import br.net.dac.msconta.model.entity.Conta;
import br.net.dac.msconta.model.entity.Movimentacao;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.event.ContaDeletedEvent;
import br.net.dac.msconta.model.event.ContaUpdatedEvent;
import br.net.dac.msconta.model.event.MovimentacaoCreatedEvent;
import br.net.dac.msconta.model.exception.ContaInativaException;
import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
import br.net.dac.msconta.rabbitMQ.ContaProdutor;
import br.net.dac.msconta.repository.ContaRepository;
import br.net.dac.msconta.repository.MovimentacaoRepository;

@Service
public class ContaCommandService {

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaProdutor contaProdutor;

    // 1. MÉTODOS
    // 1.1 VALIDAR SE CONTA É VALIDA
    private void validaConta(ContaRequestDTO conta) {
    if (conta.getClienteCpf() == null) throw new IllegalArgumentException("MsConta: Id do cliente == null");
    if (conta.getClienteCpf() == null) throw new IllegalArgumentException("MsConta: Id do gerente == null");
    if (conta.getSalario() == null) throw new IllegalArgumentException("Salario vazio");        
    }

    // 1.2 Criar numero conta aleatório de 4 dígitos
    private String geraNumeroConta() {
        Random random = new Random();
        String novoNumeroConta;

        do {
            int numero = random.nextInt(10000);
            novoNumeroConta = String.format("%04d", numero);
        } while (contaRepository.existsByNumero(novoNumeroConta));
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
        Double limite = 0.0;
        if(requestDTO.getSalario() > 2000) {
            limite = requestDTO.getSalario() / 2;
        }
        
        Conta conta = new Conta();
        conta.setNumero(geraNumeroConta());
        conta.setAtivo(true);
        conta.setGerenteCpf(requestDTO.getGerenteCpf());
        conta.setClienteCpf(requestDTO.getClienteCpf());
        conta.setSaldo(0.0);
        conta.setLimite(limite);
        conta.setData(new Date(System.currentTimeMillis()));
        
        Conta contaAdicionada = contaRepository.save(conta);

        // Dispara evento pro conta query
        contaProdutor.contaCriacaoSucesso(
            new ContaCreatedEvent (
                contaAdicionada.getNumero(),
                contaAdicionada.getGerenteCpf(),
                contaAdicionada.getClienteCpf(),
                contaAdicionada.getData(),
                contaAdicionada.getSaldo(),
                contaAdicionada.getLimite(),
                contaAdicionada.getAtivo()
            )
        );

        return new ContaResponseDTO(
            contaAdicionada.getAtivo(),
            contaAdicionada.getGerenteCpf(),
            contaAdicionada.getClienteCpf(),
            contaAdicionada.getNumero(),
            contaAdicionada.getData(),
            contaAdicionada.getSaldo(),
            contaAdicionada.getLimite()
        );        
    }

    // 1.4 ATUALIZAR CONTA (UPDATE/PUT)
    // BOTAR 
    public ContaResponseDTO atualizarConta(String numero, ContaRequestDTO requestDTO) {
        validaConta(requestDTO);
        
        Conta contaEncontrada = contaRepository.findById(numero)
            .orElseThrow(() -> new ContaNaoEncontradaException());

        Double limite = contaEncontrada.getLimite();
        if(requestDTO.getSalario() != null) {
            limite = requestDTO.getSalario() / 2;
            if(contaEncontrada.getSaldo() < 0 && limite < Math.abs(contaEncontrada.getSaldo())) { 
                limite = Math.abs(contaEncontrada.getSaldo());
            }
        }

        Conta conta = new Conta(
            contaEncontrada.getNumero(),
            requestDTO.getGerenteCpf(),
            requestDTO.getClienteCpf(),
            contaEncontrada.getData(),
            contaEncontrada.getSaldo(),
            limite,
<<<<<<< HEAD
            true
=======
            contaEncontrada.getAtivo()
>>>>>>> f1a863361431a2faafecede9a074e2e7a192daee
        );

        Conta contaAtualizada = contaRepository.save(conta);

        return new ContaResponseDTO(
            contaAtualizada.getAtivo(),
            contaAtualizada.getGerenteCpf(),
            contaAtualizada.getClienteCpf(),
            contaAtualizada.getNumero(),
            contaAtualizada.getData(),
            contaAtualizada.getSaldo(),
            contaAtualizada.getLimite()
        );
    }


    // 1.4 ATUALIZAR CONTA (UPDATE/PUT)
    // BOTAR 
    public ValorDTO atualizarLimite(String numero, ValorDTO salario) {
        
        Conta contaEncontrada = contaRepository.findById(numero)
            .orElseThrow(() -> new ContaNaoEncontradaException());

        Double limite = salario.getValor() / 2;
        if(contaEncontrada.getSaldo() < 0 && limite < Math.abs(contaEncontrada.getSaldo())) { 
            limite = Math.abs(contaEncontrada.getSaldo());
        }

        Conta conta = new Conta(
            contaEncontrada.getNumero(),
            contaEncontrada.getGerenteCpf(),
            contaEncontrada.getClienteCpf(),
            contaEncontrada.getData(),
            contaEncontrada.getSaldo(),
            limite,
            contaEncontrada.getAtivo()
        ); 

        Conta contaAtualizada = contaRepository.save(conta);

        return new ValorDTO(contaAtualizada.getLimite());
        
    }

    // 1.5 DELETAR CONTA (DELETE)
    public void desativarConta (String numero) {
        
        // VALIDAÇÃO
            // BUSCA CONTA SE EXISTE
                Conta contaEncontrada = contaRepository.findById(numero)
                    .orElseThrow(() -> new ContaNaoEncontradaException());
                // CHECK SE ENCONTRADO


            
            // CHECK SE INATIVO
                if(!contaEncontrada.getAtivo()) throw new ContaInativaException();
          
        // DESATIVA CONTA
        contaEncontrada.setAtivo(false);
        
        // Dispara evento pra contaquery
        Conta contaDesativada = contaRepository.save(contaEncontrada);
        contaProdutor.contaDeleteSucesso(
            new ContaDeletedEvent(
                contaDesativada.getAtivo(),
                contaDesativada.getNumero(),
                contaDesativada.getClienteCpf(),
                contaDesativada.getGerenteCpf()                
            )
        );
}




    public TransferenciaResponseDTO transferir(String numero, TransferenciaRequestDTO dto) {
        Conta origem = contaRepository.findById(numero)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));
        Conta destino = contaRepository.findById(dto.getDestino())
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + dto.getDestino()));

        if (origem.getSaldo() + origem.getLimite() < dto.getValor()) {
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
        movimentacao.setOrigem(null);
        movimentacao.setDestino(conta);
        movimentacao.setValor(valor);
        movimentacao.setData(new Date(System.currentTimeMillis()));
        movimentacao.setTipo("DEPOSITO");

        Conta contaAlterada = contaRepository.save(conta);
        contaProdutor.contaUpdateSucesso(
            new ContaUpdatedEvent(
            contaAlterada.getNumero(),
            contaAlterada.getGerenteCpf(),
            contaAlterada.getSaldo(),
            contaAlterada.getLimite()
            )
        );

        Movimentacao movimentacaoInserida = movimentacaoRepository.save(movimentacao);
        contaProdutor.movimentacaoCreateSucesso(
            new MovimentacaoCreatedEvent(
                movimentacaoInserida.getId(),
                movimentacaoInserida.getTipo(),
                movimentacaoInserida.getValor(),
                movimentacaoInserida.getData(),
                movimentacaoInserida.getOrigem() != null ? movimentacaoInserida.getOrigem().getClienteCpf() : null,
                movimentacaoInserida.getDestino().getClienteCpf()
            )
        );

        return new OperacaoResponseDTO(
            conta.getNumero(),
            movimentacao.getData(), 
            conta.getSaldo());
    }
    public OperacaoResponseDTO sacar(String numero, Double valor) {
        Conta conta = contaRepository.findById(numero)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

        if (conta.getSaldo() + conta.getLimite() < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo() - valor);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setOrigem(null);
        movimentacao.setDestino(conta);
        movimentacao.setValor(valor);
        movimentacao.setData(new Date(System.currentTimeMillis()));
        movimentacao.setTipo("SAQUE");

        contaRepository.save(conta);

        movimentacaoRepository.save(movimentacao);    

        return new OperacaoResponseDTO(
            conta.getNumero(),
            movimentacao.getData(), 
            conta.getSaldo());
    }

    public void redistribuiContasGerenteDeletado(String gerenteCpf) {
        List<Conta> contas = contaRepository.findByGerenteCpf(gerenteCpf);
        String novoGerente = contaRepository.findGerenteWithLeastActiveContas()
            .orElseThrow(() -> new RuntimeException("Nenhum gerente disponível"));

        for (Conta conta : contas) {
            
            conta.setGerenteCpf(novoGerente);
            contaRepository.save(conta);
        }
    }

    public void distribuiContaGerenteNovo(String novoGerenteCpf) {
        String gerenteCpf = contaRepository.findGerenteComMaisContasAtivasEMenorSaldoPositivo();

        if (gerenteCpf == null) return;

        // Busca todas as contas ativas do gerente encontrado
        List<Conta> contasDoGerente = contaRepository.findByGerenteCpfAndAtivoTrue(gerenteCpf);

        if (contasDoGerente.isEmpty()) return;

        // Pega uma conta aleatória
        Conta contaSelecionada = contasDoGerente.get(new Random().nextInt(contasDoGerente.size()));

        // Troca o gerente
        contaSelecionada.setGerenteCpf(novoGerenteCpf);
        contaRepository.save(contaSelecionada);
    }


}