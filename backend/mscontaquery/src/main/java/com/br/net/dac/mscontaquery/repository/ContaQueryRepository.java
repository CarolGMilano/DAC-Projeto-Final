package com.br.net.dac.mscontaquery.repository;

import java.util.List;
import java.util.Optional;
import com.br.net.dac.mscontaquery.model.Conta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaQueryRepository extends JpaRepository<Conta, Long> {
    public Optional<Conta> findById(Long id);
    public List<Conta> findByIdGerente(Long idGerente);
}
