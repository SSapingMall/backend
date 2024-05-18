package com.ssafy.springbootapi.domain.post.dto;

import com.ssafy.springbootapi.domain.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
