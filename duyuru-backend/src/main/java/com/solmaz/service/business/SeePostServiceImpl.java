package com.solmaz.service.business;

import com.solmaz.dto.request.SeePostRequest;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.SeePost;
import com.solmaz.repository.SeePostRepository;
import com.solmaz.service.PostService;
import com.solmaz.service.SeePostService;
import com.solmaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeePostServiceImpl implements SeePostService {
    private final SeePostRepository seePostRepository;
    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Override
    public List<SeePost> seePost(SeePostRequest seePostRequest) {
        var user = userService.findById(seePostRequest.getUserId());
        var post = postService.findById(seePostRequest.getPostId());
        var seePost = new SeePost();
        seePost.setPost(post);
        seePost.setSeenByUser(user);
        seePostRepository.save(seePost);
        return seePostRepository.findAll();
    }
    @Override
    public List<UserResponse> getAllSeenUsers(String postId) {
        var response = seePostRepository.findAllSeenUsers(postId);
        return response;
    }
}
