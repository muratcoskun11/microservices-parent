package com.solmaz.service;

import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.entity.Group;

import java.util.List;

public interface GroupService {

    GroupResponse groupDetail(String groupId);

    List<PostResponse> groupPosts(String groupId);

    Group save(Group group);

    void removeGroup(String groupId);

    List<GroupResponse> getGroups();

    Group findById(String id);

    List<GroupResponse> searchByGroupNameStartsWith(String nameStartsWith);

    GroupResponse createEmptyGroup(String groupName);

    List<Group> findAllById(Iterable<String> ids);
}
