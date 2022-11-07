package com.solmaz.controller;

import com.solmaz.dto.response.PostResponse;
import com.solmaz.service.PostService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("api/v1/post")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class PostController {
    private final PostService postService;
    private final TokenProvider tokenProvider;

    // rootURL= localhost:7070/duyuru/api/v1/post
    @GetMapping("/getPost")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostResponse> getById(@RequestParam String postId) {
        return ResponseEntity.ok(postService.getById(postId));
    }
    @DeleteMapping("/removePost")
    @ResponseStatus(HttpStatus.OK)
    public void removePost(@RequestParam String postId) {
        postService.removePost(postId);
    }
}
