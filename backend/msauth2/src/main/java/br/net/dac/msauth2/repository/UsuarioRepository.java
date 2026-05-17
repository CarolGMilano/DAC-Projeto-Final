package br.net.dac.msauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.net.dac.msauth2.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
  Optional<Usuario> findByEmail(String email);
}