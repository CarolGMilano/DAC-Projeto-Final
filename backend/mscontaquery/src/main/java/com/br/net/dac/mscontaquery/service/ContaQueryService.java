package com.br.net.dac.mscontaquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.repository.ContaQueryRepository;

public class ContaQueryService {

    @Autowired
    ContaQueryRepository contaRepository;

    public List<Conta> getContasByGerente(Long idGerente) {
        return contaRepository.findByIdGerente(idGerente);
    }

}
