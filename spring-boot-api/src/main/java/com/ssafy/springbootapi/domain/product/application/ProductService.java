package com.ssafy.springbootapi.domain.product.application;

import java.util.List;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.getReferenceById(id);
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
