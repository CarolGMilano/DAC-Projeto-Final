package br.net.dac.mscliente2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.mscliente2.model.dto.ClienteAprovacaoDTO;
import br.net.dac.mscliente2.model.dto.ClienteInsercaoDTO;
import br.net.dac.mscliente2.model.dto.ClienteRejeicaoDTO;
import br.net.dac.mscliente2.model.dto.RejeicaoDTO;
import br.net.dac.mscliente2.model.exception.CPFDuplicadoException;
import br.net.dac.mscliente2.model.exception.ClienteNaoEncontradoException;
import br.net.dac.mscliente2.model.exception.UsuarioDuplicadoException;
import br.net.dac.mscliente2.service.ClienteService;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {
  @Autowired
  private ClienteService clienteService;

  @PostMapping
  public ResponseEntity<?> inserir(@RequestBody ClienteInsercaoDTO clienteInsercaoDTO){
    try {
      ClienteInsercaoDTO clienteAdicionado = clienteService.inserirCliente(clienteInsercaoDTO);

      return ResponseEntity.status(HttpStatus.CREATED).body(clienteAdicionado);
    } catch (CPFDuplicadoException e) {
      //Como pode ter mais de um conflito, podemos tipar eles pra colocar no input do frontend a mensagem correta. 
      return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "cpf", "mensagem", e.getMessage())));
    } catch (UsuarioDuplicadoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "usuario", "mensagem", e.getMessage())));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inserir cliente: " + e.getMessage());
    }
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<?> consultar(@PathVariable String cpf) {
    try {
      ClienteInsercaoDTO clienteEncontrado = clienteService.consultarClientePorCPF(cpf);

      return ResponseEntity.ok(clienteEncontrado);
    } catch (ClienteNaoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar cliente: " + e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String filtro) {
    try {
      List<ClienteInsercaoDTO> clientes = new ArrayList<>();

      if ("para_aprovar".equals(filtro)) {
        clientes = clienteService.listarPendentes();
      } else {
        clientes = clienteService.listarAtivos();
      }

      return ResponseEntity.ok(clientes);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar clientes: " + e.getMessage());
    }
  }

  @PostMapping("/{cpf}/aprovar")
  public ResponseEntity<?> aprovar(@PathVariable String cpf) {
    try {
      ClienteAprovacaoDTO clienteAprovado = clienteService.aprovarCliente(cpf);

      return ResponseEntity.ok(clienteAprovado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao aprovar cliente: " + e.getMessage());
    }
  }

  @PostMapping("/{cpf}/rejeitar")
  public ResponseEntity<?> rejeitar(@PathVariable String cpf, @RequestBody RejeicaoDTO dto) {
    try {
      System.out.println(dto);
      ClienteRejeicaoDTO clienteRejeitado = clienteService.rejeitarCliente(cpf, dto);

      return ResponseEntity.ok(clienteRejeitado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao aprovar cliente: " + e.getMessage());
    }
  }
}
