package br.net.dac.msgerente.rabbitMQ;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Binding;

@Configuration
public class RabbitMQConfig {
  //Nome da exchange (central de mensagens). Todos os micros podem enviar mensagens para ela
  public static final String EXCHANGE = "bantads.exchange";

  //Nome da fila do msgerente. É aqui que o msgerente vai receber as mensagens dele
  public static final String CRIACAO_QUEUE = "msgerente.criacao.queue";
  public static final String ALTERACAO_QUEUE = "msgerente.alteracao.queue";
  public static final String DELECAO_QUEUE = "msgerente.delecao.queue";

  //Routing keys de ENTRADA do msgerente, são os tipos de mensagens que o msgerente vai CONSUMIR
  public static final String CRIACAO = "MSGERENTE.CRIACAO";
  public static final String ALTERACAO = "MSGERENTE.ALTERACAO";
  public static final String DELECAO = "MSGERENTE.DELECAO";

  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> applicationRApplicationListener(RabbitAdmin rabbitAdmin){
    return event -> rabbitAdmin.initialize();
  }

  @Bean
  public MessageConverter messageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter());
    return template;
  }

  //Cria a exchange (central de mensagens). Ela não guarda mensagens, só roteia
  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(EXCHANGE);
  }

  //Cria a fila do msgerente (onde as mensagens ficam esperando serem consumidas)
  @Bean
  public Queue criacaoQueue() {
      return new Queue(CRIACAO_QUEUE);
  }

  @Bean
  public Queue alteracaoQueue() {
      return new Queue(ALTERACAO_QUEUE);
  }

  @Bean
  public Queue delecaoQueue() {
      return new Queue(DELECAO_QUEUE);
  }

  /*
    Cria a ligação (binding) entre exchange e fila. 
    Por exemplo: toda mensagem com routing key "MSGERENTE.CRIACAO" será enviada pra a fila "msgerente.queue"
  */
  @Bean
  public Binding bindingCriacao() {
    return BindingBuilder
      .bind(criacaoQueue())
      .to(exchange())
      .with(CRIACAO);
  }

  @Bean
  public Binding bindingAlteracao() {
    return BindingBuilder
      .bind(alteracaoQueue())
      .to(exchange())
      .with(ALTERACAO);
  }

  @Bean
  public Binding bindingDelecao() {
    return BindingBuilder
      .bind(delecaoQueue())
      .to(exchange())
      .with(DELECAO);
  }
}