package com.solmaz.repository;

import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.SeePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeePostRepository extends JpaRepository<SeePost,String> {
    @Query(value = "select new com.solmaz.dto.response.UserResponse(s.seenByUser.userId,s.seenByUser.fullName,s.seenByUser.title,s.seenByUser.photoUrl) from SeePost s where s.post.postId=?1")
    List<UserResponse> findAllSeenUsers(String postId);
}
