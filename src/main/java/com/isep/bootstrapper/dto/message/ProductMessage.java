package com.isep.bootstrapper.dto.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMessage {
    private UUID productId;
    private String sku;
    private String designation;
    private String description;
}
