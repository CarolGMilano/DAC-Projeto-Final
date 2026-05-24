package br.net.dac.mscliente2.repository;
import br.net.dac.mscliente2.model.entity.Cliente;
import org.springframework.data.jpa.repository.*;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  public Cliente findByCpf(String cpf);
  Cliente findByIdUsuario(String idUsuario);
}
