package com.solmaz.service.business;

import com.solmaz.dto.response.PostResponse;
import com.solmaz.service.PostService;
import com.solmaz.service.UserPostService;
import com.solmaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {
    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Override
    public List<PostResponse> getUserPosts(String userId) {
        var user =userService.findById(userId);
        var posts = user.getPosts();
        var responseList = posts.stream().map(post -> modelMapper.map(post,PostResponse.class)).toList();
        return responseList;
    }
}
