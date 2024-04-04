package com.ssafy.springbootapi.domain.post.domain;


import com.ssafy.springbootapi.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long likes;

    private Long dislikes;

    private Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(long l, String firstPost, LocalDateTime now, LocalDateTime now1, long l1, long l2, long l3, User user) {
    }


    public Post() {

    }
}
