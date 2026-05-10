package br.net.dac.mscliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.net.dac.mscliente.model.dto.ClienteDTO;
import br.net.dac.mscliente.service.ClienteService;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @PostMapping
    public ResponseEntity<?> autocadastro(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteCriado = clienteService.cadastrarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    
    @GetMapping
    public ResponseEntity<?> listarTodos(
            @RequestParam(required = false) String filtro) {
        try {
            if ("para_aprovar".equals(filtro)) {
                return ResponseEntity.ok(clienteService.listarParaAprovar());
            } else if ("adm_relatorio_clientes".equals(filtro)) {
                return ResponseEntity.ok(clienteService.listarRelatorioAdm());
            } else if ("melhores_clientes".equals(filtro)) {
                return ResponseEntity.ok(clienteService.listarMelhoresClientes());
            } else {
                return ResponseEntity.ok(clienteService.listarClientes());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    
    @GetMapping("/{cpf}")
    public ResponseEntity<?> consultarPorCpf(@PathVariable String cpf) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorCpf(cpf);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }


    @PutMapping("/{cpf}")
    public ResponseEntity<?> alterarPerfil(
            @PathVariable String cpf,
            @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteDTO.setCpf(cpf);
            ClienteDTO clienteAlterado = clienteService.alterarCliente(clienteDTO);
            return ResponseEntity.ok(clienteAlterado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao alterar cliente: " + e.getMessage());
        }
    }

  
    @PostMapping("/{cpf}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable String cpf) {
        try {
            clienteService.aprovarCliente(cpf);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao aprovar cliente: " + e.getMessage());
        }
    }

  
    @PostMapping("/{cpf}/rejeitar")
    public ResponseEntity<?> rejeitar(
            @PathVariable String cpf,
            @RequestBody Map<String, String> body) {
        try {
            String motivo = body.get("motivo");
            clienteService.rejeitarCliente(cpf, motivo);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao rejeitar cliente: " + e.getMessage());
        }
    }

    
    @GetMapping("/id/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable Long id) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }

    // GET /clientes/usuario/{idUsuario} - Consultar por idUsuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> consultarPorIdUsuario(@PathVariable Long idUsuario) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorIdUsuario(idUsuario);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }
}