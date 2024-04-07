package com.ssafy.springbootapi.domain.post.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.post.application.PostService;
import com.ssafy.springbootapi.domain.post.domain.Post;

import com.ssafy.springbootapi.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    void getAllPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(Arrays.asList(
                new Post(1L, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()),
                new Post(2L, "Second post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User())
        ));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(
                        new Post(1L, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()),
                        new Post(2L, "Second post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User())
                ))));
    }

    @Test
    void getPostById() throws Exception {
        Long postId = 1L;
        Optional<Post> post = Optional.of(new Post(postId, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()));
        when(postService.getPostById(postId)).thenReturn(post);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{id}", postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(post.get())));
    }

    @Test
    void createPost() throws Exception {
        Post post = new Post(1L, "New post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User());

        when(postService.savePost(ArgumentMatchers.any(Post.class))).thenReturn(post);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post))) // ObjectMapper를 사용하여 객체를 JSON 형식으로 변환
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(post))); // ObjectMapper를 사용하여 객체를 JSON 형식으로 변환하여 비교
    }


    @Test
    void deletePost() throws Exception {
        Long postId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{id}", postId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(postService, times(1)).deletePost(postId);
    }
}
