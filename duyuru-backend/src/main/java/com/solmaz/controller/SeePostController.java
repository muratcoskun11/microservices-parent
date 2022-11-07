package com.solmaz.controller;

import com.solmaz.dto.request.SeePostRequest;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.entity.SeePost;
import com.solmaz.service.SeePostService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("api/v1/seePost")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SeePostController {
    private final SeePostService seePostService;
    private final TokenProvider tokenProvider;

    @PostMapping("/see")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SeePost>> seePost(@RequestParam String postId) {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(seePostService.seePost(new SeePostRequest(userId,postId)));
    }
    @PostMapping("/postSeenUsers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponse>> getAllSeenUsers(@RequestParam String postId) {
        return ResponseEntity.ok(seePostService.getAllSeenUsers(postId));
    }
}
