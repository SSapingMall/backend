package com.ssafy.springbootapi.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ProductOutput extends ProductListOutput{
    private String description;
    private int stock;
}
