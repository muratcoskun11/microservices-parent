package com.solmaz.service;

import com.solmaz.dto.response.PostResponse;

import java.util.List;

public interface UserPostService {

    List<PostResponse> getUserPosts(String userId);
}
