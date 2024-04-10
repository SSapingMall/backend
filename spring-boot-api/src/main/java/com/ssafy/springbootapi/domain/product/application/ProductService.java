package com.ssafy.springbootapi.domain.product.application;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import com.ssafy.springbootapi.domain.product.exception.NotFoundProductException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductListOutput> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductListOutput.class))
                .collect(Collectors.toList());
    }

    public ProductOutput getProductById(Long id) {
        Product product = findProductByIdOrThrow(id);
        return modelMapper.map(product, ProductOutput.class);
    }

    public ProductOutput insertProduct(ProductInput productInput) {
        Product product = modelMapper.map(productInput, Product.class);
        return modelMapper.map(productRepository.save(product), ProductOutput.class);
    }

    public ProductOutput removeProduct(Long id) {
        Product toRemove = findProductByIdOrThrow(id);
        productRepository.delete(toRemove);
        return modelMapper.map(toRemove, ProductOutput.class);
    }


    public ProductOutput updateProduct(Long id, ProductInput productInput) {
        Product productToUpdate = findProductByIdOrThrow(id);

        // 업데이트 로직
        productToUpdate.setCategory(productInput.getCategory());
        productToUpdate.setStock(productInput.getStock());
        productToUpdate.setImageUrl(productInput.getImageUrl());

        productRepository.save(productToUpdate);

        return modelMapper.map(productToUpdate, ProductOutput.class);
    }

    // ProductService 클래스 내부에 private 메소드 추가
    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductException("Product not found with id: " + id));
    }

}