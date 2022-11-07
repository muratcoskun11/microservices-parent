package com.solmaz.service;

import com.solmaz.dto.response.PostResponse;
import com.solmaz.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findById(String postId);


    void removePost(String postId);

    Post save(Post post);

    PostResponse getById(String postId);
}
