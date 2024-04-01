package com.ssafy.springbootapi.domain.product.api;

import java.util.List;

import com.ssafy.springbootapi.domain.product.application.ProductService;
import com.ssafy.springbootapi.domain.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }
}
