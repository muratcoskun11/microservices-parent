package com.solmaz.repository;

import com.solmaz.entity.Post;
import com.solmaz.entity.PrivatePost;
import com.solmaz.entity.UserPrivatePostRecevier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPrivatePostRepository extends JpaRepository<UserPrivatePostRecevier,String> {
    @Query(value = "select uppr.post from UserPrivatePostRecevier uppr where uppr.user.userId=?1")
    List<PrivatePost> findAllPostsByUserUserId(String userId);

}
