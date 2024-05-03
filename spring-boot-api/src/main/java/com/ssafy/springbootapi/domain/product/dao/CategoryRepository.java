package com.ssafy.springbootapi.domain.product.dao;

import com.ssafy.springbootapi.domain.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
