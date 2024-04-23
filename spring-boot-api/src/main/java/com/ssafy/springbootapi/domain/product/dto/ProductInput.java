package com.ssafy.springbootapi.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "user id is required")
    private Long user_id;
}