package com.ssafy.springbootapi.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductListOutput extends ProductBase{
    private Long id;

    public ProductListOutput() {
        super();
    }

    public ProductListOutput(String imageUrl, String name, int price, Long id) {
        super(imageUrl, name, price);
        this.id = id;
    }

}
