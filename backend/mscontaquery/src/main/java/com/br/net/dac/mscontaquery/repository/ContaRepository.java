package com.br.net.dac.mscontaquery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.net.dac.mscontaquery.model.entity.Conta;


public interface ContaRepository extends JpaRepository<Conta, String> {

    // Buscar contas de um gerente
    @Query("SELECT c.gerenteCpf FROM Conta c GROUP BY c.gerenteCpf ORDER BY COUNT(c) ASC LIMIT 1")
    Optional<String> findGerenteWithLeastActiveContas();
    Optional<Conta> findByClienteCpf(String cpf);
    Conta findTop1ByClienteCpf(String clienteCpf);

}