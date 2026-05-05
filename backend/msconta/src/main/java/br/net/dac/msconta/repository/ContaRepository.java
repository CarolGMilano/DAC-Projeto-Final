package br.net.dac.msconta.repository;

import org.springframework.data.jpa.repository.*;

import br.net.dac.msconta.model.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, String>{
    public Conta findByNumeroConta(String numeroConta);
    public boolean existsByNumeroConta(String numeroConta);
}
