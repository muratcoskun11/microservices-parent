package com.solmaz.repository;

import com.solmaz.entity.GroupPrivatePostRecevier;
import com.solmaz.entity.Post;
import com.solmaz.entity.PrivatePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupPrivatePostRepository extends JpaRepository<GroupPrivatePostRecevier,String> {
    @Query(value = "select gpr.post from GroupPrivatePostRecevier as gpr where (select gm from GroupMember as gm where gm.user.userId=?1) member of gpr.group.groupMemberList ")
    List<PrivatePost> findAllReceivedPostsByUserId(String userId);
}
