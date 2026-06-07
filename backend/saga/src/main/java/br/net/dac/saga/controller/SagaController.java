package br.net.dac.saga.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.net.dac.saga.model.contexto.SagaAutocadastroContexto;
import br.net.dac.saga.model.contexto.SagaInsercaoContexto;
import br.net.dac.saga.model.dto.AutocadastroDTO;
import br.net.dac.saga.model.dto.AutocadastroRespostaDTO;
import br.net.dac.saga.model.dto.GerenteCriacaoRespostaDTO;
import br.net.dac.saga.model.dto.GerenteDTO;
import br.net.dac.saga.model.dto.SagaRespostaDTO;
import br.net.dac.saga.service.AutocadastroSagaService;
import br.net.dac.saga.service.InsercaoGerenteSagaService;

@CrossOrigin
@RestController
public class SagaController {
  @Autowired
  private InsercaoGerenteSagaService insercaoGerenteSagaService;

  @Autowired
  private AutocadastroSagaService autocadastroSagaService;

  @PostMapping("/gerentes")
  public ResponseEntity<?> inserir(@RequestBody GerenteDTO dto) {
    SagaInsercaoContexto contexto = insercaoGerenteSagaService.iniciar(dto);

    return ResponseEntity.accepted().body(Map.of(
      "idSaga", contexto.getIdSaga(),
      "status", contexto.getStatus()
    ));
  }

  @PostMapping("/clientes")
  public ResponseEntity<?> inserirCliente(@RequestBody AutocadastroDTO dto) {
    SagaAutocadastroContexto contexto = autocadastroSagaService.iniciar(dto);

    return ResponseEntity.accepted().body(Map.of(
      "idSaga", contexto.getIdSaga(),
      "status", contexto.getStatus()
    ));
  }

  @GetMapping("/gerentes/status/{idSaga}")
  public ResponseEntity<?> status(@PathVariable String idSaga) {
    SagaInsercaoContexto contexto = insercaoGerenteSagaService.buscar(idSaga);

    SagaRespostaDTO resposta = new SagaRespostaDTO();

    resposta.setStatus(contexto.getStatus());
    resposta.setCodigoErro(contexto.getCodigoErro());
    resposta.setMensagem(contexto.getErro());

    if ("PROCESSANDO".equals(contexto.getStatus())) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(resposta);
    }

    if ("FALHA".equals(contexto.getStatus())) {
      String erro = contexto.getCodigoErro();

      if (erro == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
      }

      switch (erro) {
        case "EMAIL_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("tipo", "email","mensagem", resposta.getMensagem()));

        case "CPF_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "cpf", "mensagem", resposta.getMensagem())));

        case "USUARIO_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "usuario", "mensagem", resposta.getMensagem())));

        case "USUARIO_NAO_ENCONTRADO":
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", resposta.getMensagem()));

        default:
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
      }
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(
      new GerenteCriacaoRespostaDTO(
        contexto.getCpf(),
        contexto.getNome(),
        contexto.getEmail(),
        contexto.getTipo()
      )
    );
  }

  @GetMapping("/clientes/status/{idSaga}")
  public ResponseEntity<?> statusAutocadastro(@PathVariable String idSaga) {
    SagaAutocadastroContexto contexto = autocadastroSagaService.buscar(idSaga);

    SagaRespostaDTO resposta = new SagaRespostaDTO();

    resposta.setStatus(contexto.getStatus());
    resposta.setCodigoErro(contexto.getCodigoErro());
    resposta.setMensagem(contexto.getErro());

    if ("PROCESSANDO".equals(contexto.getStatus())) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(resposta);
    }

    if ("FALHA".equals(contexto.getStatus())) {
      String erro = contexto.getCodigoErro();

      if (erro == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
      }

      switch (erro) {
        case "EMAIL_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("tipo", "email","mensagem", resposta.getMensagem()));

        case "CPF_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "cpf", "mensagem", resposta.getMensagem())));

        case "USUARIO_DUPLICADO":
          return ResponseEntity.status(HttpStatus.CONFLICT).body((Map.of( "tipo", "usuario", "mensagem", resposta.getMensagem())));

        case "USUARIO_NAO_ENCONTRADO":
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", resposta.getMensagem()));

        default:
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
      }
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(
      new AutocadastroRespostaDTO(
        contexto.getCpf(),
        contexto.getEmail()
      )
    );
  }
}