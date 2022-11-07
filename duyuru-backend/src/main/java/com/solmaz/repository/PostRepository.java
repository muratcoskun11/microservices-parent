package com.solmaz.repository;

import com.solmaz.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,String> {
    List<Post> findAllByUserUserId(String userId);
}
