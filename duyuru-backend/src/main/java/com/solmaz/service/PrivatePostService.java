package com.solmaz.service;

import com.solmaz.dto.request.AddPrivatePostRequest;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;

import java.util.HashSet;
import java.util.List;

public interface PrivatePostService {
    PostReceiverResponse sendPrivatePost(String userId, AddPrivatePostRequest addPrivatePostRequest);

    HashSet<PostResponse> getUserPrivatePosts(String userId, String pin);

    List<PostResponse> getPrivatePostsSentByUser(String userId);
}
