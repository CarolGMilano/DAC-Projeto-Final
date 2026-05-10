package br.net.dac.mscliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.net.dac.mscliente.model.dto.ClienteDTO;
import br.net.dac.mscliente.model.entity.ClienteEntity;
import br.net.dac.mscliente.service.ClienteService;
import br.net.dac.mscliente.model.exception.ClienteNaoEncontradoException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // R1 - Autocadastro
    @PostMapping
    public ResponseEntity<?> autocadastro(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteCriado = clienteService.cadastrarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // R4 - Alteração de perfil (menos CPF)
    @PutMapping("/{cpf}")
    public ResponseEntity<?> alterarPerfil(@PathVariable String cpf, @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteDTO.setCpf(cpf);
            ClienteDTO clienteAlterado = clienteService.alterarCliente(clienteDTO);
            return ResponseEntity.ok(clienteAlterado);
        } catch (ClienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao alterar cliente: " + e.getMessage());
        }
    }

    // R12 - Listar todos os clientes (usado pelo gerente)
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<ClienteDTO> clientes = clienteService.listarClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    // R13 - Consultar cliente por CPF (usado pelo gerente)
    @GetMapping("/{cpf}")
    public ResponseEntity<?> consultarPorCpf(@PathVariable String cpf) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorCpf(cpf);
            return ResponseEntity.ok(cliente);
        } catch (ClienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }

    // Consultar por ID (usado internamente por outros microsserviços)
    @GetMapping("/id/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable Long id) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (ClienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }

    // Consultar por idUsuario (usado pelo ms-auth)
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> consultarPorIdUsuario(@PathVariable Long idUsuario) {
        try {
            ClienteDTO cliente = clienteService.consultarClientePorIdUsuario(idUsuario);
            return ResponseEntity.ok(cliente);
        } catch (ClienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao consultar cliente: " + e.getMessage());
        }
    }
}