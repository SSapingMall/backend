package com.ssafy.springbootapi.domain.product.application;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import com.ssafy.springbootapi.domain.product.exception.NotFoundProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @DisplayName("상품 전체 조회 성공 테스트")
    @Test
    void 상품전체조회성공테스트() {
        // given
        Product product1 = new Product();
        Product product2 = new Product();
        ProductListOutput productListOutput1 = new ProductListOutput();
        ProductListOutput productListOutput2 = new ProductListOutput();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(modelMapper.map(product1, ProductListOutput.class)).thenReturn(productListOutput1);
        when(modelMapper.map(product2, ProductListOutput.class)).thenReturn(productListOutput2);

        // when
        List<ProductListOutput> result = productService.getAllProducts();

        // then
        assertThat(result).containsExactly(productListOutput1, productListOutput2);
    }
    @DisplayName("상품 아이디 조회 성공 테스트")
    @Test
    void 상품아이디조회성공테스트() {
        // given
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        // when
        ProductOutput result = productService.getProductById(1L);

        // then
        assertThat(result).isEqualTo(productOutput);
    }
    @DisplayName("상품 아이디 조회 실패 : 존재하지 않는 아이디 테스트")
    @Test
    void 상품아이디조회실패존재하지않는아이디테스트() {
        // given
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // when then
        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: 1");
    }
    @DisplayName("상품 삽입 성공 테스트")
    @Test
    void 상품삽입성공테스트() {
        // given
        ProductInput productInput = new ProductInput();
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(modelMapper.map(productInput, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        // when
        ProductOutput result = productService.insertProduct(productInput);

        // then
        assertThat(result).isEqualTo(productOutput);
    }

    @DisplayName("상품 삭제 성공 테스트")
    @Test
    void 상품삭제성공테스트() {
        // given
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productRepository).delete(product);
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        // when
        ProductOutput result = productService.removeProduct(1L);

        // then
        assertThat(result).isEqualTo(productOutput);
    }
    @DisplayName("상품 삭제 실패 : 존재하지 않는 아이디 테스트")
    @Test
    void 상품삭제실패존재하지않는아이디테스트() {
        // given
        Long invalidProductId = 1L;
        when(productRepository.findById(invalidProductId)).thenReturn(java.util.Optional.empty());

        // when then
        assertThatThrownBy(() -> productService.removeProduct(invalidProductId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: " + invalidProductId);
    }
    @DisplayName("상품 수정 성공 테스트")
    @Test
    void 상품수정성공테스트() {
        // given
        int newCategory = 2;
        int newStock = 20;
        String newImageUrl = "newImageUrl";

        ProductInput productInput = new ProductInput();
        productInput.setCategory(newCategory);
        productInput.setStock(newStock);
        productInput.setImageUrl(newImageUrl);

        Product product = new Product();
        product.setId(1L);

        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        // when
        ProductOutput result = productService.updateProduct(1L, productInput);

        // then
        assertThat(result).isEqualTo(productOutput);
        assertThat(product.getCategory()).isEqualTo(newCategory);
        assertThat(product.getStock()).isEqualTo(newStock);
        assertThat(product.getImageUrl()).isEqualTo(newImageUrl);
    }
    @DisplayName("상품 수정 실패 : 존재하지 않는 아이디 테스트")
    @Test
    void 상품수정실패존재하지않는아이디테스트() {
        // given
        Long invalidProductId = 1L;
        ProductInput productInput = new ProductInput();
        productInput.setCategory(1);
        productInput.setStock(10);
        productInput.setImageUrl("testUrl");

        when(productRepository.findById(invalidProductId)).thenReturn(java.util.Optional.empty());

        // when then
        assertThatThrownBy(() -> productService.updateProduct(invalidProductId, productInput))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: " + invalidProductId);
    }
}
