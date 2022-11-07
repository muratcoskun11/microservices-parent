package com.solmaz.repository;

import com.solmaz.entity.Post;
import com.solmaz.entity.UserPostReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPostReceiverRepository extends JpaRepository<UserPostReceiver,String> {
    @Query(value = "select upr.post from UserPostReceiver upr where upr.user.userId=?1")
    List<Post> findAllPostsByUserId(String userId);
}
