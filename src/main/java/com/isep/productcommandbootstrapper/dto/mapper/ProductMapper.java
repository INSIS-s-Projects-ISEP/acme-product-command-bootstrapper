package com.isep.productcommandbootstrapper.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.isep.productcommandbootstrapper.dto.message.ProductMessage;
import com.isep.productcommandbootstrapper.model.Product;

@Component
public class ProductMapper {

    public ProductMessage toMessage(Product product){
        return new ProductMessage(
            product.getProductId(),
            product.getSku(),
            product.getDesignation(),
            product.getDescription()
        );
    }

    public List<ProductMessage> toMessageList(List<Product> products){
        return (products.stream()
            .map(this::toMessage)
            .collect(Collectors.toList())
        );
    }

}
