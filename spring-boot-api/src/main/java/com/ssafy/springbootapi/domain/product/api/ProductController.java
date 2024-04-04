package com.ssafy.springbootapi.domain.product.api;

import java.util.List;

import com.ssafy.springbootapi.domain.product.application.ProductService;
import com.ssafy.springbootapi.domain.product.domain.Product;
import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Product 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "모든 제품 조회")
    @GetMapping("")
    public ResponseEntity<List<ProductListOutput>> getAllProducts() {
        List<ProductListOutput> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "제품 ID로 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ProductOutput> getProductById(@PathVariable Long id) {
        ProductOutput product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "새 제품 추가")
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductInput productInput) {
        // ProductInput을 Product 엔티티로 변환
        Product product = new Product();
        product.setName(productInput.getName());
        product.setImageUrl(productInput.getImageUrl());
        product.setPrice(productInput.getPrice());
        product.setDescription(productInput.getDescription());
        product.setCategory(productInput.getCategory());
        product.setStock(productInput.getStock());
        product.setUserId(productInput.getUser_id());

        // 서비스 레이어를 통해 비즈니스 로직 처리
        Product newProduct = productService.insertProduct(product);

        // 생성된 Product 객체 반환
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "제품 정보 업데이트")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @Operation(summary = "제품 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}