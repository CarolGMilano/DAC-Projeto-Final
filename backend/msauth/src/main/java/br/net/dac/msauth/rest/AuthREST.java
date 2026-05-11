package br.net.dac.msauth.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.net.dac.msauth.model.entity.Login;
import br.net.dac.msauth.model.entity.Usuario;

@CrossOrigin
@RestController
@RequestMapping
public class AuthREST {

    private static final Logger logger = LoggerFactory.getLogger(AuthREST.class);

    private final List<Usuario> usuarios = new ArrayList<>();

    public AuthREST() {
        Usuario admin = new Usuario(1L, "admin@teste.com", "123", "ADMINISTRADOR");
        Usuario gerente = new Usuario(2L, "gerente@teste.com", "123", "GERENTE");
        Usuario cliente = new Usuario(3L, "cliente@teste.com", "123", "CLIENTE");

        usuarios.add(admin);
        usuarios.add(gerente);
        usuarios.add(cliente);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Login login) {
        logger.info("Login attempt for {}", login.getLogin());

        for (Usuario u : usuarios) {
            logger.debug("Checking user {}", u.getLogin());
            if (u.getLogin().equals(login.getLogin()) && u.getSenha().equals(login.getSenha())) {
                logger.info("Login approved for {} with role {}", u.getLogin(), u.getTipo());
                Usuario safe = new Usuario(u.getId(), u.getLogin(), null, u.getTipo());
                return ResponseEntity.ok(safe);
            }
        }

        logger.warn("Login denied for {}", login.getLogin());
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        List<Usuario> safeList = new ArrayList<>();
        for (Usuario u : usuarios) {
            safeList.add(new Usuario(u.getId(), u.getLogin(), null, u.getTipo()));
        }
        return safeList;
    }
}

