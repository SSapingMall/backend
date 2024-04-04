package com.ssafy.springbootapi.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class PostReply {
    @Id
    @GeneratedValue
    private Long id;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
