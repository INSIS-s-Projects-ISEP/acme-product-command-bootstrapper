package com.isep.productcommandbootstrapper.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private Long productId;
    private String sku;
    private String designation;
    private String description;

}
