package com.ssafy.springbootapi.domain.post.api;

import com.ssafy.springbootapi.domain.post.application.PostService;
import com.ssafy.springbootapi.domain.post.domain.Post;
import com.ssafy.springbootapi.domain.post.dto.AddPostRequest;
import com.ssafy.springbootapi.domain.post.dto.PostResponse;
import com.ssafy.springbootapi.domain.post.dto.UpdatePostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "Post 관련 API 입니다.")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<Post> addPost(@Validated @RequestBody AddPostRequest request) {
        Post savedPost = postService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
    }



    /** 스웨거에 넣을때
     * {
     *   "page": 0, //페이지번호
     *   "size": 3, //한페이지당 몇개 표시할지
     *   "sort": [
     *     "id,asc" // id 오름차순
     *   ]
     * }
     * @param pageable
     * @return
     */
    @Operation(summary = "게시글 리스트 불러오기 페이지버전")
    @GetMapping("/api/v1/posts")
    public ResponseEntity<Page<PostResponse>> findAllPosts(Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable);
        return ResponseEntity.ok()
                .body(posts);
    }


    @Operation(summary = "게시글 아이디로 조회")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        Post post = postService.findById(id);

        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedPost);
    }
}
