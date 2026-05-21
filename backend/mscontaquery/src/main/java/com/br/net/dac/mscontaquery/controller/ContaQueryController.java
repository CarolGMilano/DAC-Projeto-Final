package com.br.net.dac.mscontaquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.net.dac.mscontaquery.model.dto.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.ExtratoResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.SaldoResponseDTO;
import com.br.net.dac.mscontaquery.service.ContaQueryService;


@RestController
@CrossOrigin
public class ContaQueryController {

    @Autowired
    private ContaQueryService service;


    @GetMapping("/contas/{numero}/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(@PathVariable String numero) { 
        return ResponseEntity.ok(service.consultarSaldo(numero)); 
    }
    
    @GetMapping("/contas/{numero}/extrato")
    public ResponseEntity<ExtratoResponseDTO> consultarExtrato(@PathVariable String numero) { 
        return ResponseEntity.ok(service.consultarExtrato(numero)); 
    }
    
    @GetMapping("/contas")
    public ResponseEntity<List<ContaResponseDTO>> buscarContas() {
        return ResponseEntity.ok(service.buscarContas());
    }
    
    @GetMapping("/contas/disponivel")
    public ResponseEntity<String> buscarGerenteDisponivel() {
        return ResponseEntity.ok(service.buscarGerenteDisponivel());
    }

}