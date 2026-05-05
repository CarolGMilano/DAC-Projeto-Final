package br.net.dac.msconta.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.dto.ContaRequestDTO;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.entity.Conta;
import br.net.dac.msconta.model.exception.ContaInativaException;
import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
// import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
import br.net.dac.msconta.repository.ContaRepository;

@Service
public class ContaCommandService {

    @Autowired
    private ContaRepository contaRepository;

    // 1. MÉTODOS
    private void validaConta(ContaRequestDTO conta) {
        if (conta.getIdCliente() == null) throw new IllegalArgumentException("MsConta: Id da do cliente == null");
        if (conta.getIdGerente() == null) throw new IllegalArgumentException("MsConta: Id do gerente == null");
        if (conta.isAtivo() == false) throw new IllegalArgumentException("Conta inativa");        
    }

    // M
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

        
    // MÉTODO CREATE
    public ContaResponseDTO inserirConta(ContaRequestDTO requestDTO) {
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

    //MÉTODO UPDATE
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

    //MÉTODO DELETE
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

}