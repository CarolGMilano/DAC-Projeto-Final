package br.net.dac.msgerente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import br.net.dac.msgerente.model.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Long>{
  public Gerente findByCpf(String cpf);
  List<Gerente> findByAtivoTrue();
  Gerente findByCpfAndAtivoTrue(String cpf);
  Gerente findByIdUsuario(Long idUsuario);
}
