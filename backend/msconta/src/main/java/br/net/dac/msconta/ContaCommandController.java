package br.net.dac.msconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.msconta.service.ContaCommandService;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.dto.OperacaoResponseDTO;
import br.net.dac.msconta.model.dto.TransferenciaRequestDTO;
import br.net.dac.msconta.model.dto.TransferenciaResponseDTO;
import br.net.dac.msconta.model.dto.ValorDTO;
import br.net.dac.msconta.model.dto.ContaRequestDTO;

    @CrossOrigin
    @RestController
public class ContaCommandController {

    @Autowired
    private ContaCommandService commandService;


    @PostMapping("/contas")
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO dto) {  
        try {
            //arrumar depois
            ContaResponseDTO response = commandService.criarConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/contas/{numero}/saldo")
    public ResponseEntity<ValorDTO> atualizarLimite(@PathVariable String numero, @RequestBody ValorDTO salario) {        
        try
        {
            ValorDTO response = commandService.atualizarLimite(numero, salario);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/contas/{numero}")
    public ResponseEntity<Void> removerConta(@PathVariable String numero) {
        try {
            commandService.desativarConta(numero);
            return ResponseEntity.noContent().build(); // DEU CERTO = 204. NO CONTENT
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //DEU RUIM = 400 BAD REQUEST
        }
    }


    @PostMapping("/contas/{numero}/depositar")
    public ResponseEntity<OperacaoResponseDTO> depositar(@PathVariable String numero, @RequestBody ValorDTO valorDTO) {
        try {
            OperacaoResponseDTO response = commandService.depositar(numero, valorDTO.getValor());
            System.out.println("EXCEÇÃO TA AQUI O TIDAL WAVE ATHADUFUHS:" + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("EXCEÇÃO TA AQUI O TIDAL WAVE ATHADUFUHS:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/contas/{numero}/transferir")
    public ResponseEntity<TransferenciaResponseDTO> transferir(@PathVariable String numero, @RequestBody TransferenciaRequestDTO dto) {
        try {
            TransferenciaResponseDTO response = commandService.transferir(numero, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/contas/{numero}/sacar")
    public ResponseEntity<OperacaoResponseDTO> sacar(@PathVariable String numero, @RequestBody Double valor) {
        try {
            OperacaoResponseDTO response = commandService.sacar(numero, valor);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/contas/gerentes/{cpf}")
    public ResponseEntity<Void> desativarGerente(@PathVariable String cpf) {
        try {
            commandService.redistribuiContasGerenteDeletado(cpf);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/contas/gerentes/{cpf}")
    public ResponseEntity<Void> distribuiConta(@PathVariable String cpf) {
        try {
            commandService.distribuiContaGerenteNovo(cpf);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}