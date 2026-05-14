package br.net.dac.msgerente.rabbitMQ;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {
  //Lembrete: deixar mais genérico o nome das filas de sucesso e falha
  public static final String COMANDO_QUEUE = "msgerente.queue.comando";
  public static final String SUCESSO_QUEUE = "msgerente.criacao.sucesso";
  public static final String FALHA_QUEUE = "msgerente.criacao.falha";

  @Bean
  public Queue comandoQueue() {
    return new Queue(COMANDO_QUEUE, true);
  }

  @Bean
  public Queue sucessoQueue() {
    return new Queue(SUCESSO_QUEUE, true);
  }

  @Bean
  public Queue falhaQueue() {
    return new Queue(FALHA_QUEUE, true);
  }
  
  @Bean
  public MessageConverter messageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
    RabbitTemplate template = new RabbitTemplate(cf);
    template.setMessageConverter(messageConverter());
    return template;
  }
}