package br.net.dac.msauth2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.net.dac.msauth2.model.dto.LoginDTO;
import br.net.dac.msauth2.model.dto.RespostaDTO;
import br.net.dac.msauth2.model.dto.UsuarioAlteracaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioCriacaoDTO;
import br.net.dac.msauth2.model.dto.UsuarioDesativacaoDTO;
import br.net.dac.msauth2.model.enums.TipoUsuarioEnum;
import br.net.dac.msauth2.model.exception.EmailDuplicadoException;
import br.net.dac.msauth2.model.exception.SenhaIncorretaException;
import br.net.dac.msauth2.model.exception.UsuarioNaoEncontradoException;
import br.net.dac.msauth2.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping()
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
    try {
      Map<String, Object> usuario = authService.login(loginDTO);

      return ResponseEntity.ok(usuario);
    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (SenhaIncorretaException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro ao realizar login: " + e.getMessage()));
    }
  }

  @PostMapping("auth/usuarios")
  public ResponseEntity<?> criarUsuario(@RequestBody UsuarioCriacaoDTO usuarioDTO) {
    try {
      RespostaDTO usuarioCriado = authService.criarUsuario(usuarioDTO);

      return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    } catch (EmailDuplicadoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("tipo", "email","mensagem", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário: " + e.getMessage());
    }
  }

  @PutMapping("auth/usuarios")
  public ResponseEntity<?> atualizarUsuario(@RequestBody UsuarioAlteracaoDTO usuarioDTO) {
    try {
      RespostaDTO usuarioAtualizado = authService.atualizarUsuario(usuarioDTO);

      return ResponseEntity.ok(usuarioAtualizado);
    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (EmailDuplicadoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("tipo", "email","mensagem", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário: " + e.getMessage());
    }
  }

  @PutMapping("auth/usuarios/desativar")
  public ResponseEntity<?> desativarUsuario(@RequestBody UsuarioDesativacaoDTO usuarioDTO) {
    try {
      RespostaDTO usuarioDesativado = authService.desativarUsuario(usuarioDTO);

      return ResponseEntity.ok(usuarioDesativado);
    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar usuário: " + e.getMessage());
    }
  }

  @GetMapping("auth/usuarios/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    try {
      RespostaDTO usuario = authService.buscarPorId(id);

      return ResponseEntity.ok(usuario);

    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar usuário: " + e.getMessage());
    }
  }

  @GetMapping("auth/usuarios/email/{email}")
  public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
    try {
      RespostaDTO usuario = authService.buscarPorEmail(email);

      return ResponseEntity.ok(usuario);

    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar usuário: " + e.getMessage());
    }
  }

  @GetMapping("auth/usuarios/funcionarios")
  public ResponseEntity<?> listarFuncionarios() {
    try {
      List<RespostaDTO> usuarios =  authService.listarUsuarios(
        List.of(TipoUsuarioEnum.ADMIN, TipoUsuarioEnum.GERENTE)
      );

      return ResponseEntity.ok(usuarios);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar usuários: " + e.getMessage());
    }
  }

  @GetMapping("auth/usuarios/clientes")
  public ResponseEntity<?> listarClientes() {
    try {
      List<RespostaDTO> usuarios =  authService.listarUsuarios(
        List.of(TipoUsuarioEnum.CLIENTE)
      );

      return ResponseEntity.ok(usuarios);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar usuários: " + e.getMessage());
    }
  }

  @DeleteMapping("auth/usuarios/{id}")
  public ResponseEntity<?> rollback(@PathVariable String id) {
    try {
      //authService.rollback(id);

      return ResponseEntity.noContent().build();
    } catch (UsuarioNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover usuário: " + e.getMessage());
    }
  }
}
