package com.ssafy.springbootapi.domain.product.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ProductBase {
    private String imageUrl;
    private String name;
    private int price;
}
