package com.br.net.dac.mscontaquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.net.dac.mscontaquery.model.dto.response.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.response.DashboardGerenteDTO;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoDTO;
import com.br.net.dac.mscontaquery.model.dto.response.RelatorioClientesDTO;
import com.br.net.dac.mscontaquery.model.dto.response.Top3ClientesDTO;
import com.br.net.dac.mscontaquery.service.ContaQueryService;

@RequestMapping("/contas")
@RestController
@CrossOrigin
public class ContaQueryController {

    @Autowired
    private ContaQueryService service;

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<ContaResponseDTO> buscarContaPorCliente(@PathVariable String cpf) { 
        return ResponseEntity.ok(service.buscarContaPorCliente(cpf)); 
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<ContaResponseDTO> buscarContaPorNumero(@PathVariable String numero) { 
        return ResponseEntity.ok(service.buscarContaPorNumero(numero)); 
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable String id) { 
        return ResponseEntity.ok(service.consultarSaldo(id)); 
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<ExtratoDTO> consultarExtrato(@PathVariable String id) { 
        return ResponseEntity.ok(service.consultarExtrato(id)); 
    }

    @GetMapping("/gerentes/{id}/clientes")
    public ResponseEntity<List<ContaResponseDTO>> listarClientesDoGerente(@PathVariable Long id) { 
        return ResponseEntity.ok(service.listarClientesDoGerente(id)); 
    }

    @GetMapping("/clientes/{cpf}/detalhes")
    public ResponseEntity<ContaResponseDTO> buscarDadosClienteConta(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarDadosClienteConta(cpf)); 
    }

    @GetMapping("/clientes/top3")
    public ResponseEntity<List<Top3ClientesDTO>> listarTop3ClientesPorSaldo() { 
        return ResponseEntity.ok(service.listarTop3ClientesPorSaldo()); 
    }

    @GetMapping("/dashboard/gerentes")
    public ResponseEntity<List<DashboardGerenteDTO>> gerarDashboardGerentes() { 
        return ResponseEntity.ok(service.gerarDashboardGerentes()); 
    }

    @GetMapping("/relatorios/clientes")
    public ResponseEntity<List<RelatorioClientesDTO>> gerarRelatorioClientes() { 
        return ResponseEntity.ok(service.gerarRelatorioClientes()); 
    }
}