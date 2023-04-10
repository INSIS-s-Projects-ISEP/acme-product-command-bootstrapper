package com.isep.productcommandbootstrapper.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMessage {
    private Long productId;
    private String sku;
    private String designation;
    private String description;
}
