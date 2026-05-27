package com.br.net.dac.mscontaquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.ContaResumoDTO;
import com.br.net.dac.mscontaquery.model.dto.ExtratoResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.SaldoResponseDTO;
import com.br.net.dac.mscontaquery.service.ContaQueryService;


@RestController
@CrossOrigin
@RequestMapping()
public class ContaQueryController {

    @Autowired
    private ContaQueryService service;

    @GetMapping("/{numero}/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(@PathVariable String numero) {
        try {
            return ResponseEntity.ok(service.consultarSaldo(numero));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{numero}/extrato")
    public ResponseEntity<ExtratoResponseDTO> consultarExtrato(@PathVariable String numero) {
        try {
            return ResponseEntity.ok(service.consultarExtrato(numero));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ContaResponseDTO>> buscarContas() {
        try {
            return ResponseEntity.ok(service.buscarContas());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/disponivel")
    public ResponseEntity<String> buscarGerenteDisponivel() {
        try {
            return ResponseEntity.ok(service.buscarGerenteDisponivel());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<ContaResumoDTO> validarClienteExiste(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(service.validarClienteExiste(cpf));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}