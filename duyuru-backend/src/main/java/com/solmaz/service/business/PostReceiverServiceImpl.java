package com.solmaz.service.business;

import com.solmaz.dto.request.AddPostRequest;
import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.repository.GroupPostReceiverRepository;
import com.solmaz.repository.UserPostReceiverRepository;
import com.solmaz.entity.*;
import com.solmaz.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostReceiverServiceImpl implements PostReceiverService {
    private final UserPostReceiverRepository userPostReceiverRepository;
    private final GroupPostReceiverRepository groupPostReceiverRepository;
    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final GroupService groupService;
    private final PostAttachementService postAttachementService;
    private final UserPostService userPostService;


    @Override
    public List<UserResponse> getAllReceivers(String postId) {
        var post = postService.findById(postId);
        var userReceivers = post.getUserPostReceivers().stream().map(UserPostReceiver::getUser).toList();
        var groupReceivers = post.getGroupPostReceivers().stream().map(GroupPostReceiver::getGroup).toList();
        var groupMembers = groupReceivers.stream().map(group -> group.getGroupMemberList().stream().map(GroupMember::getUser).toList()).toList();
        List<User> memberList = new ArrayList<>();
        groupMembers.forEach(memberList::addAll);
        memberList.addAll(userReceivers);
        var userResponseList = memberList.stream().map(user -> modelMapper.map(user,UserResponse.class)).toList();
        return userResponseList;
    }

    @Override
    public PostReceiverResponse sendPost(String userId, AddPostRequest addPostRequest) {
        var sender =userService.findById(userId);
        var post = modelMapper.map(addPostRequest, Post.class);
        post.setUser(sender);
        var savedPost =postService.save(post);
        var attachementEntities = addPostRequest.getAttachements().stream().map(attachement-> {
            var entity = postAttachementService.save(new PostAttachement());
            String filePath = null;
            try {
                filePath = postAttachementService.saveAttachementToFolder(entity.getId(),attachement);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            entity.setFilePath(filePath);
            entity.setPost(savedPost);
            return postAttachementService.save(entity);
        }).toList();
        var postWithAttachements = postService.findById(savedPost.getPostId());
        var userList = addPostRequest.getUserIdList().stream().map(userService::findById).toList();
        var groupList = addPostRequest.getGroupIdList().stream().map(groupService::findById).toList();
        userList.forEach(receiver -> {
            var userPostReceiver = new UserPostReceiver(postWithAttachements,receiver);
            userPostReceiverRepository.saveAndFlush(userPostReceiver);

        });
        groupList.forEach(group -> {
            var groupPostReceiver = new GroupPostReceiver(postWithAttachements,group);
            groupPostReceiverRepository.saveAndFlush(groupPostReceiver);
        });
        var userResponseList = userList.stream().map(user-> modelMapper.map(user, UserResponse.class)).toList();
        var groupResponseList = groupList.stream().map(group-> modelMapper.map(group, GroupResponse.class)).toList();
        return new PostReceiverResponse(savedPost.getPostId(), userResponseList , groupResponseList);
    }

    @Override
    public Set<PostResponse> getAllReceivedPosts(String userId) {
        var postsReceivedByUsers = userPostReceiverRepository.findAllPostsByUserId(userId);
        var postsReceivedByGroups = groupPostReceiverRepository.findAllPostsOfUserReceivedFromGroups(userId);
        var postsSentByUser = userPostService.getUserPosts(userId);
        var setOfResponse = new HashSet<PostResponse>();
        setOfResponse.addAll(postsReceivedByUsers.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList());
        setOfResponse.addAll(postsReceivedByGroups.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList());
        setOfResponse.addAll(postsSentByUser);
        return setOfResponse;
    }
    @Override
    public UserPostReceiver addUserReceiver(String postId, String userId) {
        var post = postService.findById(postId);
        var user = userService.findById(userId);
        var postReceiver = new UserPostReceiver(post,user);
        var savedPostReceiver = userPostReceiverRepository.save(postReceiver);
        return savedPostReceiver;
    }
    @Override
    public GroupPostReceiver addGroupReceiver(String postId, String groupId) {
        var post = postService.findById(postId);
        var group = groupService.findById(groupId);
        var groupPostReceiver = new GroupPostReceiver(post,group);
        return groupPostReceiverRepository.save(groupPostReceiver);
    }
}
