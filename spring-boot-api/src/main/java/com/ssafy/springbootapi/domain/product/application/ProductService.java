package com.ssafy.springbootapi.domain.product.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ssafy.springbootapi.domain.product.dao.ProductRepository;
import com.ssafy.springbootapi.domain.product.domain.Product;
import com.ssafy.springbootapi.domain.product.domain.ProductMapper;
import com.ssafy.springbootapi.domain.product.dto.ProductInput;
import com.ssafy.springbootapi.domain.product.dto.ProductListOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductOutput;
import com.ssafy.springbootapi.domain.product.dto.ProductUpdate;
import com.ssafy.springbootapi.domain.product.exception.NotFoundProductException;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;


    /**
     * Product 전체 조회
     * @return List(ProductListOutput)
     */
    public List<ProductListOutput> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductListOutput)
                .collect(Collectors.toList());
    }

    /**
     * Product ID로 조회
     * @param id : 조회할 상품 id
     * @return ProductOutput(DTO)
     */
    public ProductOutput getProductById(Long id) {
        Product product = findProductByIdOrThrow(id);
        return productMapper.toProductOutput(product);
    }

    /**
     * Product 삽입 메서드
     * @param productInput : Product 생성 데이터
     * @return ProductOutput(DTO)
     */
    public ProductOutput insertProduct(ProductInput productInput) {
        // user_id로 User 객체 찾기
        Optional<User> userOptional = userRepository.findById(productInput.getUser_id());
        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + productInput.getUser_id())
        );

        // ProductInput에서 Product 엔티티로 변환
        Product product = productMapper.toEntity(productInput);

        // User 객체를 Product 엔티티에 설정
        product.setUser(user);

        // Product 엔티티를 DB에 저장하고, 저장된 엔티티를 ProductOutput DTO로 변환하여 반환
        return productMapper.toProductOutput(productRepository.save(product));
    }


    /**
     * Product 삭제 메서드
     * @param id : 삭제할 product id
     * @return ProductOutput(DTO)
     */
    public ProductOutput removeProduct(Long id) {
        Product toRemove = findProductByIdOrThrow(id);
        productRepository.delete(toRemove);
        return productMapper.toProductOutput(toRemove);
    }

    /**
     * Product 수정 메서드
     * @param id : 수정할 product id
     * @param productUpdate : 수정할 product 데이터
     * @return ProductOutput(DTO)
     */
    @Transactional
    public ProductOutput updateProduct(Long id, ProductUpdate productUpdate) {
        Product productToUpdate = findProductByIdOrThrow(id);
        productMapper.updateProduct(productUpdate, productToUpdate);
        return productMapper.toProductOutput(productRepository.save(productToUpdate));
    }

    /**
     * id로 product 찾기 또는 없을 경우 Exception 발생
     * @param id : product id
     * @return Product(Entity)
     */
    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductException("Product not found with id: " + id));
    }
}
