package com.solmaz.service;

import com.solmaz.dto.request.AddGroupMemberRequest;
import com.solmaz.dto.request.AddGroupRequest;
import com.solmaz.dto.response.GroupMemberResponse;
import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.SearchResponse;
import com.solmaz.dto.response.UserResponse;

import java.util.List;

public interface GroupMembersService {
    List<UserResponse> getGroupMembers(String groupId);

    List<UserResponse> getGroupMembersOfGroups(List<String> groupIds);

    List<GroupMemberResponse> addGroupMember(AddGroupMemberRequest addGroupMemberRequest);

    List<GroupResponse> getUserGroups(String userId);

    List<GroupMemberResponse> createGroupWithMembers(String userId, AddGroupRequest addGroupRequest);

    SearchResponse searchByUserNameOrGroupNameStartsWith(String nameStartsWith);

}
