package com.isep.bootstrapper.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

    @TargetAggregateIdentifier
    private Long productId;
    private String sku;
    private String designation;
    private String description;

}
