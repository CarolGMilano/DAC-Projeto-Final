package com.br.net.dac.mscontaquery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.net.dac.mscontaquery.model.entity.Conta;

public interface ContaQueryRepository extends JpaRepository<Conta, String> {

    // Buscar contas de um gerente
    List<Conta> findByIdGerente(Long idGerente);

    Conta findByCpfCliente(String cpf);

    Conta findByNumeroConta(String numeroConta);
    List<Conta> findTop3ByOrderBySaldoDesc();
}