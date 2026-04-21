package br.net.dac.msconta.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import br.net.dac.msconta.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
    public Conta findByNumeroConta(String numeroConta);
    public Optional<Conta> findById(Long id);
}
