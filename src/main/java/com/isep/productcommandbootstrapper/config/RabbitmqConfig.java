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

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
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

    // Product Updated
    @Bean
    public FanoutExchange productUpdatedExchange(){
        return new FanoutExchange("product.product-updated");
    }

    @Bean
    public Queue productUpdatedQueue(String instanceId){
        return new Queue("product.product-updated.product-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindProductUpdated(FanoutExchange productUpdatedExchange, Queue productUpdatedQueue){
        return BindingBuilder.bind(productUpdatedQueue).to(productUpdatedExchange);
    }

    // Product Deleted
    @Bean
    public FanoutExchange productDeletedExchange(){
        return new FanoutExchange("product.product-deleted");
    }

    @Bean
    public Queue productDeletedQueue(String instanceId){
        return new Queue("product.product-deleted.product-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindProductDeleted(FanoutExchange productDeletedExchange, Queue productDeletedQueue){
        return BindingBuilder.bind(productDeletedQueue).to(productDeletedExchange);
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
