package com.solmaz.repository;

import com.solmaz.entity.Group;
import com.solmaz.entity.GroupMember;
import com.solmaz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMembersRepository extends JpaRepository<GroupMember,String> {
    boolean existsGroupMembersByGroupAndUser(Group group, User user);
    List<GroupMember> findAllByGroupGroupId(String groupId);
    List<GroupMember> findAllByUserUserId(String userId);
    List<GroupMember> findAllByGroup(Group group);
}
