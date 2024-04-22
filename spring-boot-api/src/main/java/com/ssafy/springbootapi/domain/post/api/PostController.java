package com.ssafy.springbootapi.domain.post.api;

import com.ssafy.springbootapi.domain.post.application.PostService;
import com.ssafy.springbootapi.domain.post.domain.Post;
import com.ssafy.springbootapi.domain.post.dto.AddPostRequest;
import com.ssafy.springbootapi.domain.post.dto.PostResponse;
import com.ssafy.springbootapi.domain.post.dto.UpdatePostRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<Post> addPost(@Validated @RequestBody AddPostRequest request) {
        Post savedPost = postService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        List<PostResponse> posts = postService.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        Post post = postService.findById(id);

        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedPost);
    }
}
