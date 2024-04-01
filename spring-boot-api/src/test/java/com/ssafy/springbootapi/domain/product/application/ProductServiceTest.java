package com.ssafy.springbootapi.domain.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts() {
        // given
        Product product1 = new Product("name1","img1", "desc1", 1, 10, 1, 1);
        Product product2 = new Product("name2","img2", "desc2", 1, 10, 1, 1);
        Product product3 = new Product("name3","img3", "desc3", 1, 10, 1, 1);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        // when
        List<Product> result = productService.getAllProducts();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(product1, product2, product3);
    }

    @Test
    void getProductById() {
        // given
        Product product1 = new Product("name1","img1", "desc1", 1, 10, 1, 1);
        when(productRepository.getReferenceById(1L)).thenReturn(product1);

        // when
        Product result = productService.getProductById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product1);
    }

    @Test
    void insertProduct() {
        // given
        Product product1 = new Product("name1","img1", "desc1", 1, 10, 1, 1);
        when(productRepository.save(product1)).thenReturn(product1);

        // when
        Product result = productService.insertProduct(product1);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product1);
    }

    @Test
    void removeProduct() {
        Long id = 1L;
        // given
        Product product1 = new Product("name1","img1", "desc1", 1, 10, 1,1 );
        when(productRepository.getReferenceById(id)).thenReturn(product1);
        doNothing().when(productRepository).delete(product1);

        // when
        Product removedProduct = productService.removeProduct(id);

        // then
        verify(productRepository).getReferenceById(id);
        verify(productRepository).delete(product1);
        assertThat(removedProduct).isEqualTo(product1);
    }

    @Test
    void updateProduct() {
        Long id = 1L;
        // given
        Product product1 = new Product("name1","img1", "desc1", 1, 10, 1,1);
        product1.setId(id);
        Product product2 = new Product("name2","img2", "desc2", 2, 20, 2,1);
        product2.setId(id);

        when(productRepository.getReferenceById(id)).thenReturn(product1);
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        Product newProduct = productService.updateProduct(product2);

        // then
        verify(productRepository).getReferenceById(id); // getReferenceById 메소드가 호출되었는지 검증
        verify(productRepository).save(any(Product.class)); // save 메소드가 호출되었는지 검증
        assertThat(newProduct.getImageUrl()).isEqualTo("img2"); // 업데이트된 이미지 URL 검증
        assertThat(newProduct.getStock()).isEqualTo(20); // 업데이트된 재고 수량 검증
        assertThat(newProduct.getCategory()).isEqualTo(2); // 업데이트된 카테고리 ID 검증
    }
}