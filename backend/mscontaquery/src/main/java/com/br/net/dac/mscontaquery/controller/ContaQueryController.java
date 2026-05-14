package com.br.net.dac.mscontaquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.net.dac.mscontaquery.model.dto.response.ConsultaDeClientesDTO;
import com.br.net.dac.mscontaquery.model.dto.response.ContaResponseDTO;
import com.br.net.dac.mscontaquery.model.dto.response.DashboardAdminDTO;
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
    public ResponseEntity<ContaResponseDTO> buscarContaPorCpfCliente(@PathVariable String cpfCliente) { 
        return ResponseEntity.ok(service.buscarContaPorCpfCliente(cpfCliente)); 
    }

    @GetMapping("/{numero}")
    public ResponseEntity<ContaResponseDTO> buscarContaPorNumero(@PathVariable String numeroConta) { 
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroConta)); 
    }

    @GetMapping("/{numero}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable String numeroConta) { 
        return ResponseEntity.ok(service.consultarSaldo(numeroConta)); 
    }
    
    //100%
    @GetMapping("/{numero}/extrato")
    public ResponseEntity<ExtratoDTO> consultarExtrato(@PathVariable String numeroConta) { 
        return ResponseEntity.ok(service.consultarExtrato(numeroConta)); 
    }

    //100%
    @GetMapping("/top3")
    public ResponseEntity<List<Top3ClientesDTO>> listarTop3ClientesPorSaldo() { 
        return ResponseEntity.ok(service.listarTop3ClientesPorSaldo()); 
    }

    //100%
    @GetMapping("/administradores/gerentes")
    public ResponseEntity<List<DashboardAdminDTO>> gerarDashboardDoAdmin() { 
        return ResponseEntity.ok(service.gerarDashboardDoAdmin()); 
    }

    //100%
    @GetMapping("/admininstradores/clientes")
    public ResponseEntity<List<RelatorioClientesDTO>> gerarRelatorioClientes() { 
        return ResponseEntity.ok(service.gerarRelatorioClientes()); 
    }
    
    //100%
    @GetMapping("/gerentes/clientes")
    public ResponseEntity<List<DashboardGerenteDTO>> gerarDashboardDoGerente() { 
        return ResponseEntity.ok(service.gerarDashboardDoGerente()); 
    }

    //100%
    @GetMapping("/gerentes/{id}/clientes")
    public ResponseEntity<List<ConsultaDeClientesDTO>> listarClientesDoGerente(@PathVariable Long idGerente) { 
        return ResponseEntity.ok(service.listarClientesDoGerente(idGerente)); 
    }

}