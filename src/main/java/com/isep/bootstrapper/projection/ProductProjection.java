package com.isep.bootstrapper.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.isep.bootstrapper.event.ProductCreatedEvent;
import com.isep.bootstrapper.event.ProductDeletedEvent;
import com.isep.bootstrapper.event.ProductUpdatedEvent;
import com.isep.bootstrapper.model.Product;
import com.isep.bootstrapper.repository.ProductRepository;

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
