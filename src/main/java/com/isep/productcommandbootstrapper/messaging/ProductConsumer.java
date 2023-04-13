package com.isep.productcommandbootstrapper.messaging;

import java.io.IOException;
import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.productcommandbootstrapper.command.ProductCreatedEvent;
import com.isep.productcommandbootstrapper.command.ProductDeletedEvent;
import com.isep.productcommandbootstrapper.command.ProductUpdatedEvent;
import com.isep.productcommandbootstrapper.dto.mapper.ProductMapper;
import com.isep.productcommandbootstrapper.dto.message.ProductMessage;
import com.isep.productcommandbootstrapper.model.Product;
import com.isep.productcommandbootstrapper.repository.ProductRepository;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {

    private final CommandGateway commandGateway;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @RabbitListener(queues = "#{productCreatedQueue.name}", ackMode = "MANUAL")
    public void productCreated(ProductCreatedEvent productCreatedEvent, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Product received: " + productCreatedEvent.getSku());
        try {
            commandGateway.send(productCreatedEvent);
            log.info("Product created: " + productCreatedEvent.getSku());
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to create product: " + productCreatedEvent.getSku());
            channel.basicNack(tag, false, true);
        }

    }

    @RabbitListener(queues = "#{productUpdatedQueue.name}", ackMode = "MANUAL")
    public void productUpdated(ProductUpdatedEvent productUpdatedEvent, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Product received: " + productUpdatedEvent.getSku());
        try {
            commandGateway.send(productUpdatedEvent);
            log.info("Product updated: " + productUpdatedEvent.getSku());
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to update product: " + productUpdatedEvent.getSku());
            channel.basicNack(tag, false, true);
        }

    }
    
    @RabbitListener(queues = "#{productDeletedQueue.name}", ackMode = "MANUAL")
    public void productDeleted(String sku, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Product deleted received: " + sku);
        try {
            Product product = productRepository.findBySku(sku).orElseThrow();
            ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent(product.getProductId());
            commandGateway.send(productDeletedEvent);
            log.info("Product deleted: " + sku);
            channel.basicAck(tag, false);
        }
        catch (Exception e) {
            log.error("Fail to delete product: " + sku);
            channel.basicNack(tag, false, true);
        }

    }

    @RabbitListener(queues = "#{rpcProductQueue.name}", ackMode = "MANUAL")
    public String rpcProducts(String instanceId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("RPC Product Request received: " + instanceId);
        try {
            List<Product> products = productRepository.findAll();
            List<ProductMessage> messages = productMapper.toMessageList(products);
            String response = productMapper.toJson(messages);

            log.info("RPC Product Request sent to: " + instanceId);
            channel.basicAck(tag, false);
            return response;
        }
        catch (Exception e) {
            log.error("Error to send RPC Product Request to: " + instanceId);
            channel.basicReject(tag, true);
            return "";
        }

    }

}
