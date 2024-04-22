package com.ssafy.springbootapi.domain.post.dto;

import com.ssafy.springbootapi.domain.post.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPostRequest {
    @NotEmpty
    @NotNull
    private String title;

    @NotEmpty
    @NotNull
    private String content;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
