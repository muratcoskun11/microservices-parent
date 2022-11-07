package com.solmaz.service;

import com.solmaz.dto.request.AddPostRequest;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.GroupPostReceiver;
import com.solmaz.entity.UserPostReceiver;

import java.util.List;
import java.util.Set;

public interface PostReceiverService {
    List<UserResponse> getAllReceivers(String postId);

    PostReceiverResponse sendPost(String userId, AddPostRequest addPostRequest);
    Set<PostResponse> getAllReceivedPosts(String userId);

    UserPostReceiver addUserReceiver(String postId, String userId);

    GroupPostReceiver addGroupReceiver(String postId, String groupId);
}
