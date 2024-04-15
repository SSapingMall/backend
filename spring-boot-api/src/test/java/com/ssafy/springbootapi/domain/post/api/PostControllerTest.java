//package com.ssafy.springbootapi.domain.post.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.springbootapi.domain.post.application.PostService;
//import com.ssafy.springbootapi.domain.post.domain.Post;
//
//import com.ssafy.springbootapi.domain.user.domain.User;
//import com.ssafy.springbootapi.global.error.GlobalExceptionHandler;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class PostControllerTest {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private PostService postService;
//
//    @InjectMocks
//    private PostController postController;
//
//    private MockMvc mockMvc;
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(postController)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build();
//    }
//
//    @DisplayName("게시글 전부 조회 성공 테스트")
//    @Test
//    void 게시글전부조회성공테스트() throws Exception {
//
//        //given
//        when(postService.getAllPosts()).thenReturn(Arrays.asList(
//                new Post(1L, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()),
//                new Post(2L, "Second post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User())
//        ));
//
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
//                //then
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(
//                        new Post(1L, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()),
//                        new Post(2L, "Second post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User())
//                ))));
//    }
//    @DisplayName("게시글 전부 조회 실패 테스트")
//    @Test
//    void 게시글전부조회실패테스트() throws Exception {
//        //given
//        when(postService.getAllPosts()).thenThrow(new RuntimeException());
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
//                .andExpect(status().isInternalServerError());
//
//    }
//
//
//    @DisplayName("게시글 아이디로 조회 성공 테스트")
//    @Test
//    void 게시글아이디조회성공테스트() throws Exception {
//
//        // given
//        Long postId = 1L;
//        Optional<Post> post = Optional.of(new Post(postId, "First post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User()));
//        when(postService.getPostById(postId)).thenReturn(post);
//
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{id}", postId))
//                //then
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(post.get())));
//    }
//    @DisplayName("게시글 아이디로 조회 실패 테스트")
//    @Test
//    void 게시글아이디조회실패테스트() throws Exception {
//        // given
//        Long postId = 1L;
//        when(postService.getPostById(postId)).thenThrow(new EntityNotFoundException());
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{id}", postId))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @DisplayName("게시글 생성 성공 테스트")
//    @Test
//    void 게시글생성성공테스트() throws Exception {
//        //given
//        Post post = new Post(1L, "New post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User());
//
//        when(postService.savePost(any(Post.class))).thenReturn(post);
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(post))) // ObjectMapper를 사용하여 객체를 JSON 형식으로 변환
//                //then
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(post))); // ObjectMapper를 사용하여 객체를 JSON 형식으로 변환하여 비교
//    }
//    @DisplayName("게시글 생성 실패 테스트")
//    @Test
//    void 게시글생성실패테스트() throws Exception {
//        //given
//        Post post = new Post(1L, "New post", LocalDateTime.now(), LocalDateTime.now(), 0L, 0L, 0L, new User());
//
//        when(postService.savePost(any(Post.class))).thenThrow(new RuntimeException());
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(post)))
//                .andExpect(status().isInternalServerError());
//    }
//
//
//
//    @DisplayName("게시글 삭제 성공 테스트")
//    @Test
//    void 게시글삭제성공테스트() throws Exception {
//        //given
//        Long postId = 1L;
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{id}", postId))
//                //then
//                .andExpect(status().isNoContent());
//
//        verify(postService, times(1)).deletePost(postId); //postService의 deletePost 메소드가 정확히 한 번 호출되었는지 검증
//    }
//    @DisplayName("게시글 삭제 실패 테스트")
//    @Test
//    void 게시글삭제실패테스트() throws Exception {
//        //given
//        Long postId = 1L;
//        doThrow(new RuntimeException()).when(postService).deletePost(postId);
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{id}", postId))
//                .andExpect(status().isInternalServerError());
//    }
//
//
//    @DisplayName("게시글 수정 성공 테스트")
//    @Test
//    public void 게시글수정성공테스트() throws Exception {
//        //given
//        Post post = new Post();
//        post.setId(1L);
//        post.setContents("Updated content");
//
//        given(postService.updatePost(any(Long.class), any(Post.class))).willReturn(post);
//
//        //when
//        mockMvc.perform(put("/api/v1/posts/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(post)))
//                //then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.contents").value("Updated content"));
//    }
//    @DisplayName("게시글 수정 실패 테스트")
//    @Test
//    public void 게시글수정실패테스트() throws Exception {
//        //given
//        Post post = new Post();
//        post.setId(1L);
//        post.setContents("Updated content");
//
//        given(postService.updatePost(any(Long.class), any(Post.class))).willThrow(new RuntimeException());
//
//        //when, then
//        mockMvc.perform(put("/api/v1/posts/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(post)))
//                .andExpect(status().isInternalServerError());
//    }
//
//}
