package com.ssafy.springbootapi.domain.post.application;

import com.ssafy.springbootapi.domain.post.dao.PostRepository;
import com.ssafy.springbootapi.domain.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setContents("Test Contents");
    }
    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 전부 조회 성공 테스트")
    @Test
    void 게시글전부조회성공테스트() {
        //given when
        when(postRepository.findAll()).thenReturn(Arrays.asList(post));
        //then
        assertThat(postService.getAllPosts()).hasSize(1);
    }

    @DisplayName("게시글 전부 조회 예외 테스트")
    @Test
    void 게시글전부조회예외테스트() {
        when(postRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> postService.getAllPosts());
    }

    @DisplayName("게시글 아이디로 조회 성공 테스트")
    @Test
    void 게시글아이디조회테스트() {
        //given when
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        //then
        assertThat(postService.getPostById(1L)).isNotEmpty();
    }
    @DisplayName("게시글 아이디로 조회 예외 테스트")
    @Test
    void 게시글아이디조회예외테스트() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(postService.getPostById(1L)).isEmpty();
    }

    @DisplayName("게시글 등록 성공 테스트")
    @Test
    void 게시글등록테스트() {
        //given when
        when(postRepository.save(any(Post.class))).thenReturn(post);
        Post savedPost = postService.savePost(new Post());
        //then
        assertThat(savedPost.getContents()).isEqualTo("Test Contents");
    }
    @DisplayName("게시글 등록 예외 테스트")
    @Test
    void 게시글등록예외테스트() {
        when(postRepository.save(any(Post.class))).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> postService.savePost(new Post()));
    }



    @DisplayName("게시글 삭제 성공 테스트")
    @Test
    void 게시글삭제테스트() {
        //given
        doNothing().when(postRepository).deleteById(1L);
        //when
        postService.deletePost(1L);
        //then
        verify(postRepository, times(1)).deleteById(1L);
    }
    @DisplayName("게시글 삭제 예외 테스트")
    @Test
    void 게시글삭제예외테스트() {
        doThrow(new RuntimeException()).when(postRepository).deleteById(1L);
        assertThrows(RuntimeException.class, () -> postService.deletePost(1L));
    }


    @DisplayName("게시글 수정 성공 테스트")
    @Test
    void 게시글수정테스트() {
        //given
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        Post updatedPost = new Post();
        //when
        updatedPost.setContents("Updated Contents");
        Post result = postService.updatePost(1L, updatedPost);
        //then
        assertThat(result.getContents()).isEqualTo("Updated Contents");
    }
    @DisplayName("게시글 수정 존재안함 테스트")
    @Test
    void 게시글수정존재안함테스트() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        Post updatedPost = new Post();
        updatedPost.setContents("Updated Contents");
        assertThrows(RuntimeException.class, () -> postService.updatePost(1L, updatedPost));
    }
    @DisplayName("게시글 수정 예외 테스트")
    @Test
    void 게시글수정예외테스트() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenThrow(new RuntimeException());
        Post updatedPost = new Post();
        updatedPost.setContents("Updated Contents");
        assertThrows(RuntimeException.class, () -> postService.updatePost(1L, updatedPost));
    }

}