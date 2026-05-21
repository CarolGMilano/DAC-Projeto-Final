package br.net.dac.msconta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import br.net.dac.msconta.model.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, String>{
    public boolean existsByNumero(String numero);
    List<Conta> findByGerenteCpf(String gerenteCpf);
    List<Conta> findByGerenteCpfAndAtivoTrue(String gerenteCpf);
    @Query(value = "SELECT c.gerente_cpf FROM conta c WHERE c.ativo = true GROUP BY c.gerente_cpf ORDER BY COUNT(c) ASC LIMIT 1", nativeQuery = true)
    Optional<String> findGerenteWithLeastActiveContas();

    @Query("""
        SELECT c.gerenteCpf
        FROM Conta c
        WHERE c.ativo = true
        GROUP BY c.gerenteCpf
        ORDER BY
            COUNT(c.numero) DESC,
            SUM(CASE WHEN c.saldo > 0 THEN c.saldo ELSE 0 END) ASC
        LIMIT 1
    """)
    String findGerenteComMaisContasAtivasEMenorSaldoPositivo();

}
