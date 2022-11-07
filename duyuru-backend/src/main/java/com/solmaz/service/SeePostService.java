package com.solmaz.service;

import com.solmaz.dto.request.SeePostRequest;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.SeePost;

import java.util.List;

public interface SeePostService {
    List<SeePost> seePost(SeePostRequest seePostRequest);

    List<UserResponse> getAllSeenUsers(String postId);
}
