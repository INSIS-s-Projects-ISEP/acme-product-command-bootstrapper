package com.isep.productcommandbootstrapper.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // Product Created
    @Bean
    public FanoutExchange productCreatedExchange(){
        return new FanoutExchange("product.product-created");
    }

    @Bean
    public Queue productCreatedQueue(String instanceId){
        return new Queue("product.product-created.product-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindProductCreated(FanoutExchange productCreatedExchange, Queue productCreatedQueue){
        return BindingBuilder.bind(productCreatedQueue).to(productCreatedExchange);
    }

    // Bootstrapper
    @Bean
    public FanoutExchange rpcProductExchange(){
        return new FanoutExchange("rpc.product");
    }

    @Bean
    public Queue rpcProductQueue(String instanceId){
        return new Queue("rpc.product.product-command-bootstrapper", true, false, false);
    }

    @Bean
    public Binding bindRpcProduct(FanoutExchange rpcProductExchange, Queue rpcProductQueue){
        return BindingBuilder.bind(rpcProductQueue).to(rpcProductExchange);
    }

}
