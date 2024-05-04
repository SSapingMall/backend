package com.ssafy.springbootapi.domain.post.application;

import com.ssafy.springbootapi.domain.post.dao.PostRepository;
import com.ssafy.springbootapi.domain.post.domain.Post;
import com.ssafy.springbootapi.domain.post.dto.AddPostRequest;
import com.ssafy.springbootapi.domain.post.dto.PostResponse;
import com.ssafy.springbootapi.domain.post.dto.UpdatePostRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post save(AddPostRequest request) {
        return postRepository.save(request.toEntity());
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public Post update(long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        post.update(request.getTitle(), request.getContent());

        return post;
    }


    //페이징 기능
    public Page<PostResponse> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponse::new); // 여기서 post를 PostReponse객체로 전부 변환
        //이렇게 해주면 Post가 외부에 노출되는 것을 방지하고, 필요한 데이터만을 담은 DTO(PostResponse)를 클라이언트에 반환하게됨
    }

}