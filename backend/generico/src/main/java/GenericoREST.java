import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.net.bantads.generico.model.ClassExemplo;


    @CrossOrigin
    @RestController
public class GenericoREST {

    public static List<ClassExemplo> lista = new ArrayList<>();

    @GetMapping("exemplo")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {        
        return entity;
    }
    
    @PutMapping("path/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {        
        return entity;
    }

    @DeleteMapping("path/{id}")
    public String deleteMethidName(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    static {
        lista.add(new ClassExemplo(1, "toddy", "1234"));
        lista.add(new ClassExemplo(2, "teste", "4321"));
        lista.add(new ClassExemplo(3, "exemplo", "2143"));
    }


}
