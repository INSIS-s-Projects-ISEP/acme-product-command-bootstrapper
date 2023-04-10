package com.isep.productcommandbootstrapper.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.productcommandbootstrapper.command.ProductCreatedEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private Long productId;
    private String sku;
    private String designation;
    private String description;
    
    @CommandHandler
    public ProductAggregate(ProductCreatedEvent productCreatedEvent){
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.sku = productCreatedEvent.getSku();
        this.productId = productCreatedEvent.getProductId();
        this.designation = productCreatedEvent.getDesignation();
        this.description = productCreatedEvent.getDescription();
    }

}
