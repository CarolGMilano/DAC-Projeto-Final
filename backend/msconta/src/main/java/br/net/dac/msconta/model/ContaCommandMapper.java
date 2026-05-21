package br.net.dac.msconta.model;

import br.net.dac.msconta.model.dto.ContaDTO;

public class ContaCommandMapper {

    public static ContaDTO toDTO(Conta conta) {
        ContaResponseDTO DTO = new ContaResponseDTO();
            DTO.numero = conta.getNumero();
            DTO.saldo = conta.getSaldo();
            DTO.limite = conta.getLimite();
            DTO.gerenteCpf = conta.getGerenteCpf();
            DTO.clienteCpf = conta.getClienteCpf();
            DTO.data = conta.getData();
        return DTO;
    }
}
