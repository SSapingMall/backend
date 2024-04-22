package com.ssafy.springbootapi.domain.post.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.post.dao.PostRepository;
import com.ssafy.springbootapi.domain.post.domain.Post;
import com.ssafy.springbootapi.domain.post.dto.AddPostRequest;
import com.ssafy.springbootapi.domain.post.dto.UpdatePostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PostApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        postRepository.deleteAll();
    }

    @DisplayName("게시글 생성 성공 테스트")
    @Test
    public void 게시글생성성공테스트() throws Exception {
        // given
        final String url = "/api/v1/posts";
        final String title = "title";
        final String content = "content";
        final AddPostRequest userRequest = new AddPostRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시글 생성 실패 테스트 - 유효하지 않은 입력 값")
    @Test
    public void 게시글생성실패테스트_유효하지않은입력값() throws Exception {
        // given
        final String url = "/api/v1/posts";
        final String invalidTitle = ""; // 유효하지 않은 제목
        final String invalidContent = ""; // 유효하지 않은 내용
        final AddPostRequest userRequestWithInvalidData = new AddPostRequest(invalidTitle, invalidContent);

        final String requestBody = objectMapper.writeValueAsString(userRequestWithInvalidData);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isBadRequest()); // 400 Bad Request 상태 코드 예상

        List<Post> posts = postRepository.findAll();

        // 게시글이 생성되지 않았으므로, 저장된 게시글이 없어야 함
        assertThat(posts.size()).isEqualTo(0);
    }


    @DisplayName("게시글 전부 조회 성공 테스트")
    @Test
    public void 게시글전부조회성공테스트() throws Exception {
        //given
        final String url = "/api/v1/posts";
        final String title = "title";
        final String content = "content";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("게시글 아이디 조회 성공 테스트")
    @Test
    public void 게시글아이디조회성공테스트() throws Exception {
        //given
        final String url = "/api/v1/posts/{id}";
        final String title = "title";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedPost.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

    }

    @DisplayName("게시글 삭제 성공 테스트")
    @Test
    public void 게시글삭제성공테스트() throws Exception{
        // given
        final String url = "/api/v1/posts/{id}";
        final String title = "title";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        mockMvc.perform(delete(url, savedPost.getId()))
                .andExpect(status().isOk());

        //then
        List<Post> posts = postRepository.findAll();

        assertThat(posts).isEmpty();
    }

    @DisplayName("게시글 수정 성공 테스트")
    @Test
    public void 게시글수정성공테스트() throws Exception {
        // given
        final String url = "/api/v1/posts/{id}";
        final String title = "title";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdatePostRequest request = new UpdatePostRequest(newTitle, newContent);

        //when
        ResultActions result = mockMvc.perform(put(url, savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        Post post = postRepository.findById(savedPost.getId()).get();

        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

}
