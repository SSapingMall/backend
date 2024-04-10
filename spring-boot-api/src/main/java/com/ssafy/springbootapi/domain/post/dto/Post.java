package com.ssafy.springbootapi.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Post {
    private Long id;
    private String contents;
    private Date createdAt;
    private Date updatedAt;
    private Long likes;
    private Long dislikes;
    private Long views;
    private Long userId;

}
