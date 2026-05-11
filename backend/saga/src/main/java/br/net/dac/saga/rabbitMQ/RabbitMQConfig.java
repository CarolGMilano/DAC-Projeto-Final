package br.net.dac.saga.rabbitMQ;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {  
  public static final String AUTH_QUEUE_COMANDO = "msauth.queue.comando";
  public static final String AUTH_QUEUE_SUCESSO = "msauth.queue.sucesso";
  public static final String AUTH_QUEUE_FALHA = "msauth.queue.falha";
  
  public static final String CLIENTE_QUEUE_COMANDO = "mscliente.queue.comando";
  public static final String CLIENTE_QUEUE_SUCESSO = "mscliente.queue.sucesso";
  public static final String CLIENTE_QUEUE_FALHA = "mscliente.queue.falha";
  
  public static final String GERENTE_QUEUE_COMANDO = "msgerente.queue.comando";
  public static final String GERENTE_QUEUE_SUCESSO = "msgerente.queue.sucesso";
  public static final String GERENTE_QUEUE_FALHA = "msgerente.queue.falha";
  
  public static final String CONTA_QUEUE_COMANDO = "msconta.queue.comando";
  public static final String CONTA_QUEUE_SUCESSO = "msconta.queue.sucesso";
  public static final String CONTA_QUEUE_FALHA = "msconta.queue.falha";

  //MSAUTH
  @Bean
  public Queue authComandoQueue() { return new Queue(AUTH_QUEUE_COMANDO, true); }

  @Bean
  public Queue authSucessoQueue() { return new Queue(AUTH_QUEUE_SUCESSO, true); }

  @Bean
  public Queue authFalhaQueue() { return new Queue(AUTH_QUEUE_FALHA, true); }

  //MSCLIENTE
  @Bean
  public Queue clienteComandoQueue() { return new Queue(CLIENTE_QUEUE_COMANDO, true); }

  @Bean
  public Queue clienteSucessoQueue() { return new Queue(CLIENTE_QUEUE_SUCESSO, true); }

  @Bean
  public Queue clienteFalhaQueue() { return new Queue(CLIENTE_QUEUE_FALHA, true); }

  //MSGERENTE
  @Bean
  public Queue gerenteComandoQueue() { return new Queue(GERENTE_QUEUE_COMANDO, true); }

  @Bean
  public Queue gerenteSucessoQueue() { return new Queue(GERENTE_QUEUE_SUCESSO, true); }

  @Bean
  public Queue gerenteFalhaQueue() { return new Queue(GERENTE_QUEUE_FALHA, true); }

  //MSCONTA
  @Bean
  public Queue contaComandoQueue() { return new Queue(CONTA_QUEUE_COMANDO, true); }

  @Bean
  public Queue contaSucessoQueue() { return new Queue(CONTA_QUEUE_SUCESSO, true); }

  @Bean
  public Queue contaFalhaQueue() { return new Queue(CONTA_QUEUE_FALHA, true); }
  
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