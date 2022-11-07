package com.solmaz.service.business;

import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.entity.Group;
import com.solmaz.exception.NotFoundException;
import com.solmaz.repository.GroupRepository;
import com.solmaz.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;



    private static NotFoundException groupNotFound() {
        return new NotFoundException("Group not found!");
    }

    @Override
    public GroupResponse groupDetail(String groupId) {
        return modelMapper.map(groupRepository.findById(groupId).orElseThrow(GroupServiceImpl::groupNotFound),GroupResponse.class);
    }

    @Override
    public List<PostResponse> groupPosts(String groupId) {
        var group = groupRepository.findById(groupId).orElseThrow(GroupServiceImpl::groupNotFound);
        return group.getGroupPostReceiverList().stream().map(groupPostReceiver -> modelMapper.map(groupPostReceiver.getPost(),PostResponse.class)).toList();
    }
    @Override
    public Group save(Group group){
        return groupRepository.save(group);
    }

    @Override
    public void removeGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<GroupResponse> getGroups() {
        return groupRepository.findAll().stream().map(group -> modelMapper.map(group,GroupResponse.class)).toList();
    }

    @Override
    public Group findById(String id){return groupRepository.findById(id).orElseThrow(GroupServiceImpl::groupNotFound);}
    @Override
    public List<GroupResponse> searchByGroupNameStartsWith(String nameStartsWith) {
        return groupRepository.findAllByNameStartsWith(nameStartsWith).stream().map(group -> modelMapper.map(group,GroupResponse.class)).toList();
    }

    @Override
    public GroupResponse createEmptyGroup(String groupName) {
        var group = new Group();
        group.setName(groupName);
        return modelMapper.map(save(group),GroupResponse.class);
    }
    @Override
    public List<Group> findAllById(Iterable<String> ids){
        return groupRepository.findAllById(ids);
    }

}
