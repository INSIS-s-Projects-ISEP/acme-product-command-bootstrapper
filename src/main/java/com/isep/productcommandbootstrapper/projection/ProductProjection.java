package com.isep.productcommandbootstrapper.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.isep.productcommandbootstrapper.command.ProductCreatedEvent;
import com.isep.productcommandbootstrapper.command.ProductDeletedEvent;
import com.isep.productcommandbootstrapper.command.ProductUpdatedEvent;
import com.isep.productcommandbootstrapper.model.Product;
import com.isep.productcommandbootstrapper.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductProjection {
    
    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event){
        productRepository.save(new Product(
            event.getProductId(),
            event.getSku(),
            event.getDesignation(),
            event.getDescription()
        ));
    }

    @EventHandler
    public void on(ProductUpdatedEvent event){
        Product product = productRepository.findBySku(event.getSku()).orElseThrow();
        product.setDesignation(event.getDesignation());
        product.setDescription(event.getDescription());
        productRepository.save(product);
    }

    @EventHandler
    public void on(ProductDeletedEvent event){
        productRepository.deleteById(event.getProductId());
    }

}
