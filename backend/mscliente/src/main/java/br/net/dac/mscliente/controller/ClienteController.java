package br.net.dac.mscliente.controller;

//import br.net.dac.mscliente.model.dto.ClienteDTO;
//import br.net.dac.mscliente.model.entity.ClienteEntity;
//import br.net.dac.mscliente.repository.ClienteRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> inserir(@RequestBody ClienteEntity cliente) {
        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setCliente(cliente);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(cliente));
    }

    @GetMapping("/teste")
    public String teste() {
        return "ABLUBLEU - Controller mapeado com sucesso!";
    }
}