package br.net.dac.msconta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.Conta;
import br.net.dac.msconta.model.dto.ContaDTO;
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
        if (conta.getSaldo() == null) throw new IllegalArgumentException("MsConta: Saldo da conta == null");
        if (conta.getLimite() == null) throw new IllegalArgumentException("MsConta: Limite da conta == null");      
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

        // DEPOIS VER SOBRE IdGerente e IdCliente

        conta.setNumeroConta(contaDTO.getNumeroConta());
        conta.setDataCriacao(contaDTO.getDataCriacao());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setLimite(contaDTO.getLimite());
        
        Conta contaAdicionada = contaRepository.save(conta);

        return new ContaDTO(
            contaAdicionada.getId(),
            contaAdicionada.getNumeroConta(),
            contaAdicionada.getDataCriacao(),
            contaAdicionada.getSaldo(),
            contaAdicionada.getLimite()
        );        
    }

    //MÉTODO UPDATE
    
}
