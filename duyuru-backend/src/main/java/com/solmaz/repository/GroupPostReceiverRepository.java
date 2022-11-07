package com.solmaz.repository;

import com.solmaz.entity.GroupPostReceiver;
import com.solmaz.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupPostReceiverRepository extends JpaRepository<GroupPostReceiver,String> {
    @Query(value = "select gpr.post from GroupPostReceiver as gpr where (select gm from GroupMember as gm where gm.user.userId=?1) member of gpr.group.groupMemberList ")
    List<Post> findAllPostsOfUserReceivedFromGroups(String userId);
}
