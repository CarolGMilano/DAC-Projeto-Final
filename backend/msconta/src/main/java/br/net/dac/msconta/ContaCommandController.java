package br.net.dac.msconta;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.msconta.service.ContaCommandService;
import br.net.dac.msconta.service.RebootService;
import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.dto.OperacaoResponseDTO;
import br.net.dac.msconta.model.dto.TransferenciaRequestDTO;
import br.net.dac.msconta.model.dto.TransferenciaResponseDTO;
import br.net.dac.msconta.model.dto.ValorDTO;
import br.net.dac.msconta.model.dto.VinculoRequestDTO;
import br.net.dac.msconta.model.exception.ContaNaoEncontradaException;
import br.net.dac.msconta.model.exception.SaldoInsuficienteException;
import br.net.dac.msconta.model.dto.GerenteRequestDTO;
import br.net.dac.msconta.model.dto.ContaRequestDTO;

    @CrossOrigin
    @RestController
    @RequestMapping("/contas")
public class ContaCommandController {

    @Autowired
    private ContaCommandService commandService;

    @Autowired
    private RebootService rebootService;

    @PostMapping("/reboot")
    public ResponseEntity<?> rebook() {
        try {
        rebootService.reboot();

        return ResponseEntity.ok().build();
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro no REBOOT de CONTAS: " + e.getMessage()));
        }
    }


    @PostMapping()
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO dto) {  
        try {
            //arrumar depois
            ContaResponseDTO response = commandService.criarConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/{numero}/saldo")
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

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> removerConta(@PathVariable String numero) {
        try {
            commandService.desativarConta(numero);
            return ResponseEntity.noContent().build(); // DEU CERTO = 204. NO CONTENT
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //DEU RUIM = 400 BAD REQUEST
        }
    }


    @PostMapping("/{numero}/depositar")
    public ResponseEntity<?> depositar(@PathVariable String numero, @RequestBody ValorDTO valorDTO) {
        try {
            OperacaoResponseDTO response = commandService.depositar(numero, valorDTO.getValor());
            System.out.println("PostMapping /contas/{numero}/depositar: OperacaoResponseDTO Retornado:" + response.conta + ", " + response.data + ", " + response.saldo);
            return ResponseEntity.ok(response);
        } catch (ContaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao depositar: " + e.getMessage());
        }
    }

    @PostMapping("/{numero}/transferir")
    public ResponseEntity<?> transferir(@PathVariable String numero, @RequestBody TransferenciaRequestDTO dto) {
        try {
            TransferenciaResponseDTO response = commandService.transferir(numero, dto);
            return ResponseEntity.ok(response);
        } catch (ContaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SaldoInsuficienteException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao transferência: " + e.getMessage());
        }
    }

    @PostMapping("/{numero}/sacar")
    public ResponseEntity<?> sacar(@PathVariable String numero, @RequestBody ValorDTO valorDTO) {
        try {
            OperacaoResponseDTO response = commandService.sacar(numero, valorDTO.getValor());
            return ResponseEntity.ok(response);
        } catch (ContaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SaldoInsuficienteException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao sacar: " + e.getMessage());
        }
    }

    @PostMapping("/redistribuir-gerente")
    public ResponseEntity<GerenteRequestDTO> desativarGerente(@RequestBody GerenteRequestDTO dto) {
        try {
            GerenteRequestDTO resultado = commandService.redistribuiContasGerenteDeletado(dto);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/realocar-cliente")
    public ResponseEntity<Void> realocarCliente(@RequestBody VinculoRequestDTO dto) {
        try {
            commandService.realocarCliente(dto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}