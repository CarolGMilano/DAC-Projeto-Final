package com.br.net.dac.mscontaquery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.net.dac.mscontaquery.model.entity.Conta;

public interface ContaQueryRepository extends JpaRepository<Conta, String> {

    // Buscar contas de um gerente
    Conta findByCpfClienteAndStatus(String cpf, String status);
    Conta findByNumeroAndStatus(String numero, String status);
    List<Conta> findAllByStatusOrderByClienteNomeAsc(String status);
    List<Conta> findTop3ByStatusOrderBySaldoDesc(String status);
    List<Conta> findByStatus(String status);

}