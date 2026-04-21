package br.net.dac.msconta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.Conta;
import br.net.dac.msconta.model.dto.ContaDTO;
import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
// import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
import br.net.dac.msconta.repository.ContaRepository;

@Service
public class ContaCommandService {

    @Autowired
    private ContaRepository contaRepository;

    // MÉTODOS
    private void validaConta(ContaDTO conta) {
        if (conta.getNumeroConta() == null || conta.getNumeroConta().isBlank()) throw new IllegalArgumentException("MsConta: Número da conta == null || \"\"");
        if (conta.getDataCriacao() == null) throw new IllegalArgumentException("MsConta: dataCriacao da conta == null");
        if (conta.getId() == null) throw new IllegalArgumentException("MsConta: Id da conta == null");
        if (conta.getIdCliente() == null) throw new IllegalArgumentException("MsConta: Id da do cliente == null");
        if (conta.getIdGerente() == null) throw new IllegalArgumentException("MsConta: Id do gerente == null");

    }

    // MÉTODO CREATE
    public ContaDTO inserirConta(ContaDTO contaDTO) {
        validaConta(contaDTO);

        // VALIDAÇÃO DE EXISTÊNCIA DE CONTA
        // NÃO SEI SE VAMOS USAR PRA VERIFICAR SE CONTA É ATIVA OU NÃO, ENTÃO DEIXAMOS COMENTADO CASO PRECISE
        // Conta contaEncontrado = contaRepository.findById(contaDTO.getId()).orElseThrow(ContaNaoEncontradaException::new);

        // if (!contaEncontrado.getAtivo()) {
        //     throw new ContaNaoEncontradaException();
        // }
        
        
        Conta conta = new Conta();

        conta.setAtivo(true);
        conta.setIdGerente(contaDTO.getIdGerente());
        conta.setIdCliente(contaDTO.getIdCliente());
        conta.setNumeroConta(contaDTO.getNumeroConta());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setLimite(contaDTO.getLimite());
        
        Conta contaAdicionada = contaRepository.save(conta);

        return new ContaDTO(
            contaAdicionada.isAtivo(),
            contaAdicionada.getId(),
            contaAdicionada.getIdGerente(),
            contaAdicionada.getIdCliente(),
            contaAdicionada.getNumeroConta(),
            contaAdicionada.getDataCriacao(),
            contaAdicionada.getSaldo(),
            contaAdicionada.getLimite()
        );        
    }

    //MÉTODO UPDATE
    public ContaDTO atualizarConta(ContaDTO contaDTO) {
        validaConta(contaDTO);

        Conta contaEncontrada = contaRepository.findById(contaDTO.getId()).orElseThrow(ContaNaoEncontradaException::new);

        if (contaEncontrada == null || contaEncontrada.isAtivo() == false) {
             throw new ContaNaoEncontradaException();
         }

        Conta conta = new Conta(
            contaDTO.isAtivo(),
            contaDTO.getId(),
            contaDTO.getIdGerente(),
            contaDTO.getIdCliente(),
            contaDTO.getNumeroConta(),
            contaDTO.getDataCriacao(),
            contaDTO.getSaldo(),
            contaDTO.getLimite()
        ); 

        Conta contaAtualizada = contaRepository.save(conta);

        return new ContaDTO(
            contaAtualizada.isAtivo(),
            contaAtualizada.getId(),
            contaAtualizada.getIdGerente(),
            contaAtualizada.getIdCliente(),
            contaAtualizada.getNumeroConta(),
            contaAtualizada.getDataCriacao(),
            contaAtualizada.getSaldo(),
            contaAtualizada.getLimite()
        );
    }

    //MÉTODO DELETE
    public void desativarConta (Long idConta) {
        
        // VALIDAÇÃO
            // BUSCA CONTA SE EXISTE
                Conta contaEncontrada = contaRepository
                                            .findById(idConta)
                                            .orElseThrow(ContaNaoEncontradaException::new);
            
            // CHECK SE INATIVO
                if(!contaEncontrada.isAtivo()) throw new ContaInativaException();
          
        // DESATIVA CONTA
        contaEncontrada.setAtivo(false);
        contaRepository.save(contaEncontrada);
}
