package com.solmaz.controller;

import com.solmaz.dto.request.AddPostRequest;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.UserPostReceiver;
import com.solmaz.service.PostReceiverService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/PostReceiver")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class PostReceiverController {
    private final PostReceiverService postReceiverService;
    private final TokenProvider tokenProvider;
    @PostMapping("/postReceivers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponse>> getAllReceivers(@RequestParam String postId) {
        return ResponseEntity.ok(postReceiverService.getAllReceivers(postId));
    }
    @PostMapping("/createPost")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostReceiverResponse> sendPost(@RequestBody AddPostRequest addPostRequest) {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(postReceiverService.sendPost(userId, addPostRequest));
    }
    @PostMapping("/getPosts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<PostResponse>> getAllReceivedPosts() {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(postReceiverService.getAllReceivedPosts(userId));
    }
    @PostMapping("/addReceiver")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserPostReceiver> addReceiver(@RequestParam String postId) {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(postReceiverService.addUserReceiver(postId, userId));
    }
}
