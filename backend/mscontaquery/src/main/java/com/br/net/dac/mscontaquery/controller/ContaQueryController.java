package com.br.net.dac.mscontaquery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.br.net.dac.mscontaquery.model.dto.ContaQueryMapper;
import com.br.net.dac.mscontaquery.model.dto.ContaQueryResponseDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.service.ContaQueryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/conta")
@RestController
@CrossOrigin
public class ContaQueryController {

    @Autowired
    private ContaQueryService queryService;

    // BUSCA POR CLIENTES POR GERENTE
    @GetMapping("/{idGerente}")
    public ResponseEntity<List<ContaQueryResponseDTO>> getContasByGerente(@RequestParam Long idGerente) {
        List<Conta> contas = queryService.getContasByGerente(idGerente);

        if (contas == null || contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ContaQueryResponseDTO> dtos = contas.stream()
            .map(ContaQueryMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos) ;
        
    }


}
