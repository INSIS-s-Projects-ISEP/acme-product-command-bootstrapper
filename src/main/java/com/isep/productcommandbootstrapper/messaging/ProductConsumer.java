package com.isep.productcommandbootstrapper.messaging;

import java.io.IOException;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.productcommandbootstrapper.command.ProductCreatedCommand;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {
    
    private final CommandGateway commandGateway;
    
    @RabbitListener(queues = "#{productCreatedQueue.name}", ackMode = "MANUAL")
    public void productCreated(ProductCreatedCommand productCreatedCommand, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Product received: " + productCreatedCommand.getSku());

        try {
            commandGateway.send(productCreatedCommand);
            log.info("Product created: " + productCreatedCommand.getSku());
            channel.basicAck(tag, false);
        } 
        catch (Exception e) {
            log.error("Fail to create product: " + productCreatedCommand.getSku());
            channel.basicNack(tag, false, true);
        }
        
    }
    
}
