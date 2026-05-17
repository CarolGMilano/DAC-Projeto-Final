package com.br.net.dac.mscontaquery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.net.dac.mscontaquery.model.dto.response.DashboardAdminDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;

public interface ContaQueryRepository extends JpaRepository<Conta, String> {

    // Buscar contas de um gerente
    List<Conta> findByIdGerente(Long idGerente);

    Conta findByCpfCliente(String cpf);

    Conta findByNumeroConta(String numeroConta);
    List<Conta> findTop3ByOrderBySaldoDesc();

    List<Conta> findAllByOrderByNomeClienteAsc();

    List<Conta> findByStatusConta(String statusConta);
    
    @Query("""
    SELECT new com.br.net.dac.mscontaquery.dto.DashboardAdminDTO(
        c.nomeGerente,

        SUM(CASE 
            WHEN c.saldo < 0 THEN c.saldo 
            ELSE 0 
        END),

        SUM(CASE 
            WHEN c.saldo >= 0 THEN c.saldo 
            ELSE 0 
        END),

        COUNT(c)
    )
    FROM Conta c
    GROUP BY c.nomeGerente
    ORDER BY 
        SUM(CASE 
            WHEN c.saldo >= 0 THEN c.saldo 
            ELSE 0 
        END) DESC
""")
List<DashboardAdminDTO> gerarDashboardAdmin();

}