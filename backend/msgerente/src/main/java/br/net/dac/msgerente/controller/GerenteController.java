package br.net.dac.msgerente.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.msgerente.model.dto.GerenteDTO;
import br.net.dac.msgerente.model.dto.GerenteResumoDTO;
import br.net.dac.msgerente.model.exception.CPFDuplicadoException;
import br.net.dac.msgerente.model.exception.GerenteNaoEncontradoException;
import br.net.dac.msgerente.model.exception.UsuarioDuplicadoException;
import br.net.dac.msgerente.service.GerenteService;

@CrossOrigin
@RestController
@RequestMapping("/gerentes")
public class GerenteController {
  
  @Autowired
  private GerenteService gerenteService;

  @GetMapping
  public ResponseEntity<?> listar() {
    try {
      List<GerenteResumoDTO> gerentes = gerenteService.listarGerentes();

      return ResponseEntity.ok(gerentes);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar gerentes: " + e.getMessage());
    }
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<?> consultarPorId(@PathVariable Long id) {
    try {
      GerenteDTO gerenteEncontrado = gerenteService.consultarGerentePorId(id);

      return ResponseEntity.ok(gerenteEncontrado);
    } catch (GerenteNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar gerente: " + e.getMessage());
    }
  }

  @GetMapping("/usuario/{idUsuario}")
  public ResponseEntity<?> consultarPorIdUsuario(@PathVariable Long idUsuario) {
    try {
      Long idUsuarioEncontrado = gerenteService.consultarGerenteIdUsuario(idUsuario);

      return ResponseEntity.ok(idUsuarioEncontrado);
    } catch (GerenteNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar gerente: " + e.getMessage());
    }
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<?> consultar(@PathVariable String cpf) {
    try {
      GerenteDTO gerenteEncontrado = gerenteService.consultarGerentePorCPF(cpf);

      return ResponseEntity.ok(gerenteEncontrado);
    } catch (GerenteNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar gerente: " + e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> inserir(@RequestBody GerenteDTO gerenteDTO){
    try {
      GerenteDTO gerenteAdicionado = gerenteService.inserirGerente(gerenteDTO);

      return ResponseEntity.status(HttpStatus.CREATED).body(gerenteAdicionado);
    } catch (CPFDuplicadoException e) {
      //Como pode ter mais de um conflito, podemos tipar eles pra colocar no input do frontend a mensagem correta. 
      return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "cpf", "mensagem", e.getMessage())));
    } catch (UsuarioDuplicadoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "usuario", "mensagem", e.getMessage())));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inserir gerente: " + e.getMessage());
    }
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<?> alterar(@PathVariable("cpf") String cpf, @RequestBody GerenteDTO gerenteDTO){
    try {
      gerenteDTO.setCpf(cpf);

      GerenteDTO  gerenteAlterado = gerenteService.alterarGerente(gerenteDTO);

      return ResponseEntity.ok(gerenteAlterado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar gerente: " + e.getMessage());
    }
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<?> desativar(@PathVariable("cpf") String cpf){
    try{
      GerenteDTO gerenteDeletado = gerenteService.consultarGerentePorCPF(cpf);

      gerenteService.desativarGerente(gerenteDeletado.getCpf());

      return ResponseEntity.ok(gerenteDeletado);
    } catch (GerenteNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar gerente: " + e.getMessage());
    }
  }
}
