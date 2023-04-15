package com.isep.bootstrapper.event;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatedEvent {

    @TargetAggregateIdentifier
    private UUID productId;
    private String sku;
    private String designation;
    private String description;

}
