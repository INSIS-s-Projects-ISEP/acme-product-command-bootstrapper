package com.isep.productcommandbootstrapper.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.isep.productcommandbootstrapper.command.ProductCreatedCommand;

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
    public ProductAggregate(ProductCreatedCommand productCreatedCommand){
        AggregateLifecycle.apply(productCreatedCommand);
    }

    @EventSourcingHandler
    public void on(ProductCreatedCommand productCreatedCommand){
        this.sku = productCreatedCommand.getSku();
        this.productId = productCreatedCommand.getProductId();
        this.designation = productCreatedCommand.getDesignation();
        this.description = productCreatedCommand.getDescription();
    }

}
