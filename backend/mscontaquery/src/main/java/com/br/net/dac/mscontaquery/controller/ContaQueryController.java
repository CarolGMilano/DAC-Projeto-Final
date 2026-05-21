package com.br.net.dac.mscontaquery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.net.dac.mscontaquery.model.dto.response.DadosClienteResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ItemDashboardResponse;
import com.br.net.dac.mscontaquery.model.dto.response.SaldoResponse;
import com.br.net.dac.mscontaquery.model.dto.response.ExtratoResponse;
import com.br.net.dac.mscontaquery.service.ContaQueryService;


@RestController
@CrossOrigin
public class ContaQueryController {

    @Autowired
    private ContaQueryService service;

    @GetMapping("/clientes/{cpf}")
    public ResponseEntity<DadosClienteResponse> buscarClientePorCpf(@PathVariable String cpfCliente) { 
        return ResponseEntity.ok(service.buscarContaPorCpfCliente(cpfCliente)); 
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> buscarClientes(
            @RequestParam(required = false) String filtro) { 

        switch (filtro) {

            case "para_aprovar":
                return ResponseEntity.ok(service.gerarDashboardDoGerente());

            case "admin_relatorio_clientes":
                return ResponseEntity.ok(service.gerarRelatorioClientes());

            case "melhores_clientes":
                return ResponseEntity.ok(service.listarTop3ClientesPorSaldo());

            default:
                return ResponseEntity.ok(service.listarClientes());
        }

    }

    @GetMapping("/contas/{numero}/saldo")
    public ResponseEntity<SaldoResponse> consultarSaldo(@PathVariable String numeroConta) { 
        return ResponseEntity.ok(service.consultarSaldo(numeroConta)); 
    }
    
    @GetMapping("/contas/{numero}/extrato")
    public ResponseEntity<ExtratoResponse> consultarExtrato(@PathVariable String numeroConta) { 
        return ResponseEntity.ok(service.consultarExtrato(numeroConta)); 
    }
    
     @GetMapping("/gerentes")
    public ResponseEntity<List<ItemDashboardResponse>> buscarGerentes() { 
        return ResponseEntity.ok(service.gerarDashboardDoAdmin());
    }
    

}