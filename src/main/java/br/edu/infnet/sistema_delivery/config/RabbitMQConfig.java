package br.edu.infnet.sistema_delivery.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PEDIDOS = "pedidos.v1.pedido-criado";
    public static final String EXCHANGE_PEDIDOS = "pedidos.v1.events";
    public static final String ROUTING_KEY_PEDIDO_CRIADO = "pedido.criado";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_PEDIDOS, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PEDIDO_CRIADO);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}