package com.ssafy.springbootapi.domain.product.application;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    public List<ProductListOutput> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductListOutput.class))
                .collect(Collectors.toList());
    }

    public ProductOutput getProductById(Long id) {
        Product product = productRepository.getReferenceById(id);
        return modelMapper.map(product, ProductOutput.class);
    }

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    public Product removeProduct(Long id) {
        Product toRemove = productRepository.getReferenceById(id);
        productRepository.delete(toRemove);
        return toRemove;
    }

    public Product updateProduct(Product product) {
        Product newProduct = productRepository.getReferenceById(product.getId());
        newProduct.setCategory(product.getCategory());
        newProduct.setStock(product.getStock());
        newProduct.setImageUrl(product.getImageUrl());

        productRepository.save(newProduct);

        return newProduct;
    }
}