import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.msconta.model.dto.ContaDTO;

    @CrossOrigin
    @RestController
public class ContaREST {

    static List<ContaDTO> contas = new ArrayList<>();
        
    @GetMapping("/contas")
    public String obterTodasContas() {
        //CQRS
        return new String();
    }

    @GetMapping("/contas/{id}")
    public String obterContaPorId(@PathVariable("id") int id) {
        //CQRS
        return new String();
    }
    
    @PostMapping("/contas")
    public ContaDTO inserirConta(@RequestBody ContaDTO conta) {        
        return conta;
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