package com.solmaz.repository;

import com.solmaz.entity.PrivatePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivatePostRepository extends JpaRepository<PrivatePost,String> {
}
