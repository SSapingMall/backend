package com.ssafy.springbootapi.domain.product.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Setter entity에 setter두는건 안 좋다고 해서 삭제
@Builder
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    private String description;
    private int category;
    private int stock;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(name = "user_id")
    private int userId;
    // test code를 위해 추가
    public void updateInfo(int category, int stock, String imageUrl) {
        this.category = category;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }
}