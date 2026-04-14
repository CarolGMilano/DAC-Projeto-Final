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

import br.net.dac.msconta.model.Conta;

    @CrossOrigin
    @RestController
public class ContaREST {

    static List<Conta> contas = new ArrayList<>();

    static {
        contas.add(new Conta(1, "12345-6", Date.valueOf("2023-01-10"), 1500.0, 500.0));
        contas.add(new Conta(2, "23456-7", Date.valueOf("2023-02-15"), 2500.0, 1000.0));
        contas.add(new Conta(3, "34567-8", Date.valueOf("2023-03-20"), 500.0, 300.0));
        contas.add(new Conta(4, "45678-9", Date.valueOf("2023-04-25"), 3200.0, 1500.0));
        contas.add(new Conta(5, "56789-0", Date.valueOf("2023-05-30"), 800.0, 200.0));
    }
        
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
    public Conta inserirConta(@RequestBody Conta conta) {        
        return conta;
    }
    
    @PutMapping("/contas/{id}")
    public Conta alterarConta(@PathVariable int id, @RequestBody Conta conta) {        
        return conta;
    }

    @DeleteMapping("/contas/{id}")
    public Conta removerConta(@PathVariable int id, @RequestBody Conta conta) {
        return conta;
    }

}