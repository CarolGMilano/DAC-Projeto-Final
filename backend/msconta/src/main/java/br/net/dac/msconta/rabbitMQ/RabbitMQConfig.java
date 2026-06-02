package br.net.dac.msconta.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String COMANDO_QUEUE = "msconta.queue.comando";
    public static final String VINCULO_QUEUE = "msconta.queue.vinculo";
    public static final String SUCESSO_QUEUE = "msconta.queue.sucesso";
    public static final String FALHA_QUEUE   = "msconta.queue.falha";
    public static final String QUERY_QUEUE   = "msconta.queue.query";   // CQRS: Command → Query

    @Bean public Queue comandoQueue() { return new Queue(COMANDO_QUEUE, true); }
    @Bean public Queue vinculoQueue() { return new Queue(VINCULO_QUEUE, true); }
    @Bean public Queue sucessoQueue() { return new Queue(SUCESSO_QUEUE, true); }
    @Bean public Queue falhaQueue()   { return new Queue(FALHA_QUEUE,   true); }
    @Bean public Queue queryQueue()   { return new Queue(QUERY_QUEUE,   true); }

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
