package com.solmaz.service.business;

import com.solmaz.dto.request.AddGroupMemberRequest;
import com.solmaz.dto.request.AddGroupRequest;
import com.solmaz.dto.response.GroupMemberResponse;
import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.SearchResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.Group;
import com.solmaz.entity.GroupMember;
import com.solmaz.exception.AlreadyExistException;
import com.solmaz.repository.GroupMembersRepository;
import com.solmaz.service.GroupMembersService;
import com.solmaz.service.GroupService;
import com.solmaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMembersServiceImpl implements GroupMembersService {
    private final GroupMembersRepository groupMembersRepository;
    private final GroupService groupService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Override
    public List<UserResponse> getGroupMembers(String groupId) {
        groupService.findById(groupId);
        var groupMembers =groupMembersRepository.findAllByGroupGroupId(groupId);
        return groupMembers.stream().map(groupMember -> modelMapper.map(groupMember.getUser(),UserResponse.class)).toList();
    }
    @Override
    public List<UserResponse> getGroupMembersOfGroups(List<String> groupIds) {
        List list = new ArrayList();
        groupIds.forEach(id -> getGroupMembers(id).forEach(userResponse -> list.add(userResponse)));
        return list;
    }
    @Override
    public List<GroupMemberResponse> addGroupMember(AddGroupMemberRequest addGroupMemberRequest) {
        var user = userService.findById(addGroupMemberRequest.getUserId());
        var group = groupService.findById(addGroupMemberRequest.getGroupId());

        if (groupMembersRepository.existsGroupMembersByGroupAndUser(group, user)) {
            throw new AlreadyExistException("Group member already exists");
        }
        var groupMember = new GroupMember(user,group);
        groupMembersRepository.save(groupMember);
        return groupMembersRepository.findAllByGroup(group).stream().map(groupMember1-> modelMapper.map(groupMember1,GroupMemberResponse.class)).toList();
    }
    @Override
    public List<GroupResponse> getUserGroups(String userId) {
        var groupMembers = groupMembersRepository.findAllByUserUserId(userId);
        return groupMembers.stream().map(groupMember -> modelMapper.map(groupMember.getGroup(),GroupResponse.class)).toList();
    }
    @Override
    public List<GroupMemberResponse> createGroupWithMembers(String userId, AddGroupRequest addGroupRequest) {
        var creator = userService.findById(userId);
        var group = new Group();
        group.setCreator(creator);
        group.setName(addGroupRequest.getName());
        var users = userService.findAllById(addGroupRequest.getUserIdList());
        var savedGroup = groupService.save(group);
        var list = new ArrayList<>();
        users.forEach(user ->
        {
            list.add(groupMembersRepository.save(new GroupMember(user,savedGroup)));

        });

        return list.stream().map(groupMember -> modelMapper.map(groupMember,GroupMemberResponse.class)).toList();
    }

    @Override
    public SearchResponse searchByUserNameOrGroupNameStartsWith(String nameStartsWith) {
        var userList = userService.searchByUsernameStartsWith(nameStartsWith);
        var groupList = groupService.searchByGroupNameStartsWith(nameStartsWith);
        return new SearchResponse(userList,groupList);
    }
}
