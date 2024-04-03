package com.ssafy.springbootapi.domain.user.dao;

import com.ssafy.springbootapi.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
