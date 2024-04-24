package com.ssafy.springbootapi.domain.product.domain;

import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(ProductUpdate dto, @MappingTarget Product entity);

    Product toEntity(ProductUpdate dto);
    Product toEntity(ProductInput dto);

    ProductOutput toProductOutput(Product entity);
    ProductListOutput toProductListOutput(Product entity);
}
