package com.isep.productcommandbootstrapper.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.productcommandbootstrapper.command.ProductCreatedEvent;
import com.isep.productcommandbootstrapper.command.ProductDeletedEvent;
import com.isep.productcommandbootstrapper.command.ProductUpdatedEvent;

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

    @CommandHandler
    public void handle(ProductUpdatedEvent productUpdatedEvent){
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent productUpdatedEvent){
        this.sku = productUpdatedEvent.getSku();
        this.productId = productUpdatedEvent.getProductId();
        this.designation = productUpdatedEvent.getDesignation();
        this.description = productUpdatedEvent.getDescription();
    }

    @CommandHandler
    public void handle(ProductDeletedEvent productDeletedEvent){
        AggregateLifecycle.apply(productDeletedEvent);
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent){
        AggregateLifecycle.markDeleted();
    }

    // @EventSourcingHandler
    // public void on(ProductDeletedEvent productDeletedEvent){
    // }

}
