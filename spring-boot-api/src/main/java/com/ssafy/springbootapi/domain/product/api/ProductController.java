package com.ssafy.springbootapi.domain.product.api;

import java.util.List;

import com.ssafy.springbootapi.domain.product.application.ProductService;
import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductUpdate;
import com.ssafy.springbootapi.global.aop.annotation.ToException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Product 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
//@CrossOrigin // spring security 디펜던시 삭제로 인한 주석처리
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "모든 제품 조회")
    @GetMapping("")
    public ResponseEntity<List<ProductListOutput>> getAllProducts() {
        List<ProductListOutput> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(summary = "제품 ID로 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ProductOutput> getProductById(@PathVariable Long id) {
        ProductOutput product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "새 제품 추가")
    @PostMapping("")
    public ResponseEntity<ProductOutput> createProduct(@RequestBody ProductInput productInput) {
        // 서비스 레이어를 통해 비즈니스 로직 처리
        ProductOutput newProduct = productService.insertProduct(productInput);

        // 생성된 Product 객체 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @Operation(summary = "제품 정보 업데이트")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductOutput> updateProduct(@PathVariable Long id, @RequestBody ProductUpdate productDetails) {
        // 서비스 레이어를 통해 비즈니스 로직 처리
        ProductOutput updatedProduct = productService.updateProduct(id, productDetails);

        // 업데이트된 Product 객체 반환
        return ResponseEntity.ok().body(updatedProduct);
    }
    @Operation(summary = "제품 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductOutput> deleteProduct(@PathVariable Long id) {
        ProductOutput removedProduct = productService.removeProduct(id);
        return ResponseEntity.ok().body(removedProduct);
    }
}