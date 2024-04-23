package com.ssafy.springbootapi.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ProductUpdate extends ProductBase {
    private String description;
    private int category;
    private int stock;
}
