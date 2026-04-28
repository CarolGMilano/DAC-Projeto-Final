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
import br.net.dac.msconta.service.ContaCommandService;

import br.net.dac.msconta.model.dto.ContaDTO;

    @CrossOrigin
    @RestController
public class ContaCommandController {

    @Autowired
    private ContaCommandService commandService;

    static List<ContaDTO> contas = new ArrayList<>();

    @PostMapping("/contas")
    public ResponseEntity<ContaDTO> inserirConta(@RequestBody ContaDTO dto) {  
        try {
            //arrumar depois
            ContaDTO response = commandService.inserirConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/contas/{id}")
    public ContaDTO alterarConta(@PathVariable int id, @RequestBody ContaDTO conta) {        
        return conta;
    }

    @DeleteMapping("/contas/{id}")
    public ContaDTO removerConta(@PathVariable int id, @RequestBody ContaDTO conta) {
        return conta;
    }

}