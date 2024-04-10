package com.ssafy.springbootapi.domain.product.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductInput extends ProductBase {
    private String description;
    private int category;
    private int stock;
    private int user_id;
}