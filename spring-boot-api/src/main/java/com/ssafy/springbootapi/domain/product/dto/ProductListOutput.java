package com.ssafy.springbootapi.domain.product.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ProductListOutput extends ProductBase{
    private Long id;
}
