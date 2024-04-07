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
    @DisplayName("Get All Products")
    @Test
    void getAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        ProductListOutput productListOutput1 = new ProductListOutput();
        ProductListOutput productListOutput2 = new ProductListOutput();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(modelMapper.map(product1, ProductListOutput.class)).thenReturn(productListOutput1);
        when(modelMapper.map(product2, ProductListOutput.class)).thenReturn(productListOutput2);

        List<ProductListOutput> result = productService.getAllProducts();

        assertThat(result).containsExactly(productListOutput1, productListOutput2);
    }
    @DisplayName("Get One Product")
    @Test
    void getProductById() {
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        ProductOutput result = productService.getProductById(1L);

        assertThat(result).isEqualTo(productOutput);
    }
    @DisplayName("Product Not Found")
    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: 1");
    }
    @DisplayName("Insert Product")
    @Test
    void insertProduct() {
        ProductInput productInput = new ProductInput();
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(modelMapper.map(productInput, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        ProductOutput result = productService.insertProduct(productInput);

        assertThat(result).isEqualTo(productOutput);
    }

    @DisplayName("Remove Product")
    @Test
    void removeProduct() {
        Product product = new Product();
        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productRepository).delete(product);
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        ProductOutput result = productService.removeProduct(1L);

        assertThat(result).isEqualTo(productOutput);
    }
    @DisplayName("Remove Product Failed - Not found")
    @Test
    void removeProductNotFound() {
        Long invalidProductId = 1L;
        when(productRepository.findById(invalidProductId)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> productService.removeProduct(invalidProductId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: " + invalidProductId);
    }
    @DisplayName("Update Product")
    @Test
    void updateProduct() {
        ProductInput productInput = new ProductInput();
        productInput.setCategory(2);
        productInput.setStock(20);
        productInput.setImageUrl("newImageUrl");

        Product product = new Product();
        product.setId(1L);

        ProductOutput productOutput = new ProductOutput();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(modelMapper.map(product, ProductOutput.class)).thenReturn(productOutput);

        ProductOutput result = productService.updateProduct(1L, productInput);

        assertThat(result).isEqualTo(productOutput);
        assertThat(product.getCategory()).isEqualTo(2);
        assertThat(product.getStock()).isEqualTo(20);
        assertThat(product.getImageUrl()).isEqualTo("newImageUrl");
    }
    @DisplayName("Update Product Failed - Not found")
    @Test
    void updateProductNotFound() {
        Long invalidProductId = 1L;
        ProductInput productInput = new ProductInput();
        productInput.setCategory(1);
        productInput.setStock(10);
        productInput.setImageUrl("testUrl");

        when(productRepository.findById(invalidProductId)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(invalidProductId, productInput))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessageContaining("Product not found with id: " + invalidProductId);
    }
}
