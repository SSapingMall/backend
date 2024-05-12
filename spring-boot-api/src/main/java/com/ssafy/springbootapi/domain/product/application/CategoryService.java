package com.ssafy.springbootapi.domain.product.application;

import com.ssafy.springbootapi.domain.product.dao.CategoryRepository;
import com.ssafy.springbootapi.domain.product.domain.Category;
import com.ssafy.springbootapi.domain.product.exception.NotFoundCategoryException;
import jakarta.transaction.Transactional;
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

        return categoryRepository.findById(id).orElseThrow(()
                -> new NotFoundCategoryException("Category not found with id : " + id));
    }

    /**
     * 카테고리 생성
     * @param name : 카테고리 이름
     * @return 생성된 카테고리
     */
    @Transactional
    public Category createCategory(String name) {
        try {
            Category category = new Category();
            category.setName(name);
            return categoryRepository.save(category);
        } catch(Exception E) {
            return null;
        }
    }

    /**
     * 카테고리 이름 수정
     * @param id : 수정할 카테고리 아이디
     * @param name : 수정할 카테고리 이름
     * @return 수정된 카테고리
     */
    @Transactional
    public Category updateCategory(Integer id, String name) {
        Category category = findCategoryByIdOrThrow(id);
        category.setName(name);
        return category;
    }

    /**
     * 카테고리 삭제
     * @param id : 삭제할 카테고리 아이디
     * @return 성공 여부
     */
    @Transactional
    public Boolean deleteCategory(Integer id) {
        Category category = findCategoryByIdOrThrow(id);
        try {
            categoryRepository.delete(category);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private Category findCategoryByIdOrThrow(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundCategoryException("Category Not Found"));
    }
}
