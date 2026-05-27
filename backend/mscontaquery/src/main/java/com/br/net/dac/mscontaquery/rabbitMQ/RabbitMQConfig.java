package com.br.net.dac.mscontaquery.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUERY_QUEUE = "msconta.queue.query";

    @Bean public Queue queryQueue() { return new Queue(QUERY_QUEUE, true); }

    @Bean public JacksonJsonMessageConverter producerJacksonJsonMessageConverter() { return new JacksonJsonMessageConverter(); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter producerJacksonJsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(producerJacksonJsonMessageConverter);
        return template;
    }
}
