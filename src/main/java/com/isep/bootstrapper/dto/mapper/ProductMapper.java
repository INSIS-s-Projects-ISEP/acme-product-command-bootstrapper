package com.isep.bootstrapper.dto.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.bootstrapper.dto.message.ProductMessage;
import com.isep.bootstrapper.model.Product;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final ObjectMapper objectMapper;

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

    public String toJson(List<ProductMessage> messages) throws JsonProcessingException{
        Map<String, List<ProductMessage>> dataMap = new HashMap<>();
        dataMap.put("response", messages);
        return objectMapper.writeValueAsString(dataMap);
    }

}
