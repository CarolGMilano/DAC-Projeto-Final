package com.br.net.dac.mscontaquery.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.net.dac.mscontaquery.model.dto.MovimentacaoRequestDTO;
import com.br.net.dac.mscontaquery.model.dto.MovimentacaoResponseDTO;
import com.br.net.dac.mscontaquery.model.entity.Conta;
import com.br.net.dac.mscontaquery.model.entity.Movimentacao;
import com.br.net.dac.mscontaquery.model.event.Evento;
import com.br.net.dac.mscontaquery.repository.ContaRepository;
import com.br.net.dac.mscontaquery.repository.MovimentacaoRepository;
import com.br.net.dac.mscontaquery.service.ContaQueryService;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class ContaQueryConsumidor {

    @Autowired
    private ContaQueryService contaQueryService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUERY_QUEUE)
    public void handleQueryEvent(Evento evento) {
        if (evento == null || evento.getTipo() == null) {
            System.out.println("ContaQueryConsumidor: evento nulo ou sem tipo, ignorando.");
            return;
        }

        System.out.println("ContaQueryConsumidor: evento recebido -> " + evento.getTipo());

        switch (evento.getTipo()) {
            case "CONTA_CRIADA"    -> handleContaCriada(evento);
            case "CONTA_ATUALIZADA"-> handleContaAtualizada(evento);
            case "CONTA_DESATIVADA"-> handleContaDesativada(evento);
            case "MOVIMENTACAO_CRIADA"-> handleMovimentacaoCriada(evento);
            default -> System.out.println("ContaQueryConsumidor: tipo desconhecido -> " + evento.getTipo());
        }
    }
    // CONTA_CRIADA
    // Payload (vindo do msconta ContaCreatedEvent):
    //   numero, gerenteCpf, clienteCpf, data, saldo, limite, ativo
    private void handleContaCriada(Evento evento) {
        try {
            Map<String, Object> payload = toMap(evento.getPayload());

            String numero     = (String)  payload.get("numero");
            String gerenteCpf = (String)  payload.get("gerenteCpf");
            String clienteCpf = (String)  payload.get("clienteCpf");
            Double saldo      = toDouble(payload.get("saldo"));
            Double limite     = toDouble(payload.get("limite"));
            Boolean ativo     = (Boolean) payload.get("ativo");
            
            java.sql.Date data = toSqlDate(payload.get("data"));

            Conta conta = contaRepository.findById(numero)
                    .orElse(new Conta());

            conta.setNumero(numero);
            conta.setGerenteCpf(gerenteCpf);
            conta.setClienteCpf(clienteCpf);
            conta.setSaldo(saldo);
            conta.setLimite(limite);
            conta.setAtivo(ativo != null ? ativo : true);
            conta.setData(data);

            contaRepository.save(conta);
            System.out.println("ContaQueryConsumidor: conta criada no BD de leitura -> " + numero);

        } catch (Exception e) {
            System.out.println("ContaQueryConsumidor: erro ao processar CONTA_CRIADA -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleContaAtualizada(Evento evento) {
        try {
            Map<String, Object> payload = toMap(evento.getPayload());

            String  numero =        (String)  payload.get("numero");
            Boolean ativo  =        (Boolean) payload.get("ativo");
            String gerenteCpf =     (String) payload.get("gerenteCpf");

            Conta conta = contaRepository.findById(numero)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

            conta.setAtivo(ativo != null ? ativo : conta.getAtivo());
            conta.setGerenteCpf(gerenteCpf);

            contaRepository.save(conta);
            System.out.println("ContaQueryConsumidor: conta atualizada no BD de leitura -> " + numero);

        } catch (Exception e) {
            System.out.println("ContaQueryConsumidor: erro ao processar CONTA_ATUALIZADA -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleContaDesativada(Evento evento) {
        try {
            Map<String, Object> payload = toMap(evento.getPayload());

            String numero = (String) payload.get("numero");

            Conta conta = contaRepository.findById(numero)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + numero));

            conta.setAtivo(false);

            contaRepository.save(conta);
            System.out.println("ContaQueryConsumidor: conta desativada no BD de leitura -> " + numero);

        } catch (Exception e) {
            System.out.println("ContaQueryConsumidor: erro ao processar CONTA_DESATIVADA -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleMovimentacaoCriada(Evento evento) {
        try {
            Map<String, Object> payload = toMap(evento.getPayload());
            Long id = ((Number) payload.get("id")).longValue();
            String tipo         =   (String) payload.get("tipo");
            Double valor        = toDouble(payload.get("valor"));
            java.sql.Date data  = toSqlDate(payload.get("data"));
            String origem       = (String) payload.get("origem");
            String destino      = (String) payload.get("destino");
            
            // sysout de teste
            System.out.println("-=-=-=-=-=-=-=-=- ANTES -=-=-=-=-=-=-=-=-");
            System.out.println("ID: " + id + " Tipo: " + tipo + " Valor: " + valor + " Data: " +  data + "Origem: " + origem + " Destino: " + destino);

            // Montagem do MovimentacaoRequestDTO para enviar para o Service
            MovimentacaoRequestDTO request = new MovimentacaoRequestDTO(
                id,
                tipo,
                valor,
                data, 
                origem, 
                destino
            );

            // Chamada ao service para registrar a movimentação
            MovimentacaoResponseDTO response = contaQueryService.criarMovimentacao(request);

            System.out.println("-=-=-=-=-=-=-=-=- DEPOIS -=-=-=-=-=-=-=-=-");
            System.out.println("Tipo: " + response.getTipo() + " Valor: " + response.getValor() + " Data: " +  response.getData() + "Origem: " + response.getOrigem() + " Destino: " + response.getDestino());
            
        } catch (Exception e) {
            System.out.println("ContaQueryConsumidor: erro ao processar MOVIMENTACAO_CRIADA -> " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Helpers de conversão 
    @SuppressWarnings("unchecked")
    private Map<String, Object> toMap(Object payload) {
        return objectMapper.convertValue(payload, Map.class);
    }

    private Double toDouble(Object value) {
        if (value == null) return 0.0;
        if (value instanceof Double d) return d;
        if (value instanceof Number n) return n.doubleValue();
        return Double.parseDouble(value.toString());
    }

    private java.sql.Date toSqlDate(Object value) {
        if (value == null) return new java.sql.Date(System.currentTimeMillis());
        if (value instanceof Number n) return new java.sql.Date(n.longValue());
        return new java.sql.Date(System.currentTimeMillis());
    }
}