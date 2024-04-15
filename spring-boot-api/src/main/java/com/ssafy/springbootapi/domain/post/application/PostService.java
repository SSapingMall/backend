package com.ssafy.springbootapi.domain.post.application;

import com.ssafy.springbootapi.domain.post.dao.PostRepository;
import com.ssafy.springbootapi.domain.post.domain.Post;
import com.ssafy.springbootapi.domain.post.dto.AddPostRequest;
import com.ssafy.springbootapi.domain.post.dto.UpdatePostRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}