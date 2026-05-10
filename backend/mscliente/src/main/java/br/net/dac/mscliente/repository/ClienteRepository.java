package br.net.dac.mscliente.repository;

import br.net.dac.mscliente.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional <ClienteEntity> findByIdUsuario(long idUsuario);
    List<ClienteEntity> findByStatus(String status);
}   