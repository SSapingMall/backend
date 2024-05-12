package com.ssafy.springbootapi.domain.product.api;

import com.ssafy.springbootapi.domain.product.application.CategoryService;
import com.ssafy.springbootapi.domain.product.domain.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Category 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
//@CrossOrigin // spring security 디펜던시 삭제로 인한 주석처리
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "모든 카테고리 조회")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @Operation(summary = "카테고리 생성")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody String name) {
        Category category = categoryService.createCategory(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "카테고리 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody String name) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(id, name));
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoryService.deleteCategory(id));
    }
}
