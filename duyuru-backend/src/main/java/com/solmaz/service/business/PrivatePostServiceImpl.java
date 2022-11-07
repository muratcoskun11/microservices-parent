package com.solmaz.service.business;

import com.google.common.hash.Hashing;
import com.solmaz.dto.request.AddPrivatePostRequest;
import com.solmaz.dto.response.GroupResponse;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.*;
import com.solmaz.exception.PinIsNotCorrectException;
import com.solmaz.repository.GroupPrivatePostRepository;
import com.solmaz.repository.PrivatePostRepository;
import com.solmaz.repository.UserPrivatePostRepository;
import com.solmaz.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivatePostServiceImpl implements PrivatePostService {
    private final UserPrivatePostRepository userPrivatePostRepository;
    private final GroupPrivatePostRepository groupPrivatePostRepository;
    private final UserService userService;
    private final PrivatePostRepository privatePostRepository;
    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final PrivatePostAttachementService privatePostAttachementService;

    @Override
    public PostReceiverResponse sendPrivatePost(String userId, AddPrivatePostRequest addPrivatePostRequest) {
        var sender =userService.findById(userId);
        var post = modelMapper.map(addPrivatePostRequest, PrivatePost.class);
        post.setUser(sender);
        var savedPost =privatePostRepository.save(post);
        var attachementEntities = addPrivatePostRequest.getAttachements().stream().map(attachement-> {
            var entity = privatePostAttachementService.save(new PrivatePostAttachement());
            String filePath = null;
            try {
                filePath = privatePostAttachementService.saveAttachementToFolder(entity.getId(),attachement);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            entity.setFilePath(filePath);
            entity.setPrivatePost(savedPost);
            return privatePostAttachementService.save(entity);
        }).toList();
        var postWithAttachements = privatePostRepository.findById(savedPost.getPostId()).orElseThrow(()-> new IllegalArgumentException("private post not found"));
        var userList = addPrivatePostRequest.getUserIdList().stream().map(userService::findById).toList();
        var groupList = addPrivatePostRequest.getGroupIdList().stream().map(groupService::findById).toList();
        userList.forEach(receiver -> {
            var userPostReceiver = new UserPrivatePostRecevier(postWithAttachements,receiver);
            userPrivatePostRepository.saveAndFlush(userPostReceiver);

        });
        groupList.forEach(group -> {
            var groupPostReceiver = new GroupPrivatePostRecevier(postWithAttachements,group);
            groupPrivatePostRepository.saveAndFlush(groupPostReceiver);
        });
        var userResponseList = userList.stream().map(user-> modelMapper.map(user, UserResponse.class)).toList();
        var groupResponseList = groupList.stream().map(group-> modelMapper.map(group, GroupResponse.class)).toList();
        return new PostReceiverResponse(savedPost.getPostId(), userResponseList , groupResponseList);
    }

    @Override
    public HashSet<PostResponse> getUserPrivatePosts(String userId, String pin) {
        String hashedPin = Hashing.sha256().hashString(pin, StandardCharsets.UTF_8).toString();
        if(!hashedPin.equals(userService.findById(userId).getPrivateMessagePin()))
            throw new PinIsNotCorrectException("pin is not correct");
        var postsReceivedByUsers = userPrivatePostRepository.findAllPostsByUserUserId(userId);
        var postsReceivedByGroups = groupPrivatePostRepository.findAllReceivedPostsByUserId(userId);
        var postsSentByUser = getPrivatePostsSentByUser(userId);
        var setOfResponse = new HashSet<PostResponse>();
        setOfResponse.addAll(postsReceivedByUsers.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList());
        setOfResponse.addAll(postsReceivedByGroups.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList());
        setOfResponse.addAll(postsSentByUser);
        return setOfResponse;
    }
    @Override
    public List<PostResponse> getPrivatePostsSentByUser(String userId) {
        var user =userService.findById(userId);
        var posts = user.getPrivatePosts();
        var responseList = posts.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList();
        return responseList;
    }
}
