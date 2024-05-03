package com.ssafy.springbootapi.domain.product.application;

import com.ssafy.springbootapi.domain.product.dao.CategoryRepository;
import com.ssafy.springbootapi.domain.product.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 전체 조회
     * @return List(Category)
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * id로 카테고리 조회
     * @param id : category id
     * @return Category
     */
    public Category getCategoryById(Integer id) {
        return categoryRepository.getReferenceById(id);
    }

    public Boolean createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return true;
    }
}
