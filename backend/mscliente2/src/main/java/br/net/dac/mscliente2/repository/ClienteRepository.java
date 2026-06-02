package br.net.dac.mscliente2.repository;
import br.net.dac.mscliente2.model.entity.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  public Cliente findByCpf(String cpf);
  List<Cliente> findByCpfGerente(String cpfGerente);
  Cliente findByIdUsuario(String idUsuario);
}
