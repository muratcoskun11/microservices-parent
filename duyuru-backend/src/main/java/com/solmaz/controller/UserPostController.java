package com.solmaz.controller;

import com.solmaz.dto.response.PostResponse;
import com.solmaz.service.UserPostService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("api/v1/userPost")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserPostController {
    private final UserPostService userPostService;
    private final TokenProvider tokenProvider;
    @PostMapping("/getUserPosts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostResponse>> getUserPosts() {
        var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(userPostService.getUserPosts(userId));
    }
}
