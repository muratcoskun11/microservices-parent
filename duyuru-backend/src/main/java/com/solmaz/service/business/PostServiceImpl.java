package com.solmaz.service.business;

import com.solmaz.dto.response.PostResponse;
import com.solmaz.entity.Post;
import com.solmaz.exception.NotFoundException;
import com.solmaz.repository.PostRepository;
import com.solmaz.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private static NotFoundException postNotFound() {
        return new NotFoundException("Post not found!");
    }


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(String postId) {
        return postRepository.findById(postId).orElseThrow(PostServiceImpl::postNotFound);
    }

    @Override
    public void removePost(String postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
    @Override
    public PostResponse getById(String postId){
        return modelMapper.map(findById(postId),PostResponse.class);
    }

}
