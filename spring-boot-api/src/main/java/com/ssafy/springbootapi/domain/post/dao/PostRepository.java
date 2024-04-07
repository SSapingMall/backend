package com.ssafy.springbootapi.domain.post.dao;

import com.ssafy.springbootapi.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
