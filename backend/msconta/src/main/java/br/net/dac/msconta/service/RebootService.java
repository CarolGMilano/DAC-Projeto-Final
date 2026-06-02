package br.net.dac.msconta.service;

import br.net.dac.msconta.rabbitMQ.ContaProdutor;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.dac.msconta.model.dto.MovimentacaoRequestDTO;
import br.net.dac.msconta.model.entity.Conta;
import br.net.dac.msconta.model.entity.Movimentacao;
import br.net.dac.msconta.model.event.ContaCreatedEvent;
import br.net.dac.msconta.model.event.RebootContaEvent;
import br.net.dac.msconta.repository.ContaRepository;
import br.net.dac.msconta.repository.MovimentacaoRepository;

@Service
public class RebootService {
  @Autowired
  private ContaProdutor contaProdutor;

  @Autowired
  private ContaRepository contaRepository;

  @Autowired
  private MovimentacaoRepository movimentacaoRepository;

  public void reboot() {
    List<ContaCreatedEvent> contas = new ArrayList<>();
    List<MovimentacaoRequestDTO> movimentacoes = new ArrayList<>();
    
    movimentacaoRepository.deleteAll();
    contaRepository.deleteAll();

    contas.add(inserirConta(
      "1291",
      "98574307084",
      "12912861012",
      java.sql.Date.valueOf("2000-01-01"),
      800.0,
      5000.0
    ));

    contas.add(inserirConta(
      "0950",
      "64065268052",
      "09506382000",
      java.sql.Date.valueOf("1990-10-10"),
      -10000.0,
      10000.0
    ));

    contas.add(inserirConta(
      "8573",
      "23862179060",
      "85733854057",
      java.sql.Date.valueOf("2012-12-12"),
      -1000.0,
      1500.0
    ));

    contas.add(inserirConta(
      "5887",
      "98574307084",
      "58872160006",
      java.sql.Date.valueOf("2022-02-22"),
      150000.0,
      0.0
    ));

    contas.add(inserirConta(
      "7617",
      "64065268052",
      "76179646090",
      java.sql.Date.valueOf("2025-01-01"),
      1500.0,
      0.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-01T10:10:00"),
      "DEPOSITO",
      "1291",
      null,
      1000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-01T11:10:00"),
      "DEPOSITO",
      "1291",
      null,
      900.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-01T12:10:00"),
      "SAQUE",
      "1291",
      null,
      550.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-01T13:10:00"),
      "SAQUE",
      "1291",
      null,
      350.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-10T15:10:00"),
      "DEPOSITO",
      "1291",
      null,
      2000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-15T08:10:00"),
      "SAQUE",
      "1291",
      null,
      500.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2020-01-20T12:10:00"),
      "TRANSFERENCIA",
      "1291",
      "0950",
      1700.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-01-01T12:10:00"),
      "DEPOSITO",
      "0950",
      null,
      1000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-01-02T10:10:00"),
      "DEPOSITO",
      "0950",
      null,
      5000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-01-10T10:10:00"),
      "SAQUE",
      "0950",
      null,
      200.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-02-05T10:10:00"),
      "DEPOSITO",
      "0950",
      null,
      7000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-05-05T13:10:00"),
      "DEPOSITO",
      "8573",
      null,
      1000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-05-06T14:10:00"),
      "SAQUE",
      "8573",
      null,
      2000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-06-01T18:10:00"),
      "DEPOSITO",
      "5887",
      null,
      150000.0
    ));

    movimentacoes.add(inserirMovimentacao(
      LocalDateTime.parse("2025-07-01T18:10:00"),
      "DEPOSITO",
      "7617",
      null,
      1500.0
    ));

    contaProdutor.iniciarReboot(new RebootContaEvent(contas, movimentacoes));
  }

  public ContaCreatedEvent inserirConta(String numero, String gerenteCpf, String clienteCpf, Date data, Double saldo, Double limite) {
    Conta c = new Conta();

    c.setNumero(numero);
    c.setGerenteCpf(gerenteCpf);
    c.setClienteCpf(clienteCpf);
    c.setData(data);
    c.setSaldo(saldo);
    c.setLimite(limite);
    c.setAtivo(true);

    contaRepository.save(c);

    return new ContaCreatedEvent(
      c.getNumero(),
      c.getGerenteCpf(),
      c.getClienteCpf(),
      c.getData(),
      c.getSaldo(),
      c.getLimite(),
      c.getAtivo()
    );
  }

  public MovimentacaoRequestDTO inserirMovimentacao(LocalDateTime data, String tipo, String origem, String destino, Double valor) {
    Movimentacao m = new Movimentacao();

    Conta contaOrigem = contaRepository.findByNumero(origem);
    Conta contaDestino = contaRepository.findByNumero(destino);

    m.setData(data);
    m.setTipo(tipo);
    m.setOrigem(contaOrigem);
    m.setDestino(contaDestino);
    m.setValor(valor);

    Movimentacao salva = movimentacaoRepository.save(m);

    return new MovimentacaoRequestDTO(
      salva.getId(),
      salva.getTipo(),
      salva.getValor(),
      salva.getData(),
      salva.getOrigem() != null ? salva.getOrigem().getNumero() : null,
      salva.getDestino() != null ? salva.getDestino().getNumero() : null
    );
  }
}
