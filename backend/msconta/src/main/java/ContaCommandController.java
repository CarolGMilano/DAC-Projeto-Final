import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.context.annotation.RequestScope;

import br.net.dac.msconta.service.ContaCommandService;

import br.net.dac.msconta.model.dto.ContaResponseDTO;
import br.net.dac.msconta.model.dto.ContaRequestDTO;

    @CrossOrigin
    @RestController
public class ContaCommandController {

    @Autowired
    private ContaCommandService commandService;

    static List<ContaResponseDTO> contas = new ArrayList<>();

    @PostMapping("/contas")
    public ResponseEntity<ContaResponseDTO> inserirConta(@RequestBody ContaRequestDTO dto) {  
        try {
            //arrumar depois
            ContaResponseDTO response = commandService.inserirConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaResponseDTO> alterarConta(@PathVariable String numeroConta, @RequestBody ContaRequestDTO conta) {        
        try
        {
            ContaResponseDTO response = commandService.atualizarConta(numeroConta, conta);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/contas/{id}")
    public ResponseEntity<ContaRequestDTO> removerConta(@PathVariable String numeroConta) {
        return conta;
    }

}