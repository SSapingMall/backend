package com.ssafy.springbootapi.domain.post.dao;

import com.ssafy.springbootapi.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.
}
