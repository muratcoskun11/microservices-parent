package com.solmaz.controller;

import com.solmaz.dto.request.AddPrivatePostRequest;
import com.solmaz.exception.ErrorResponse;
import com.solmaz.dto.response.PostReceiverResponse;
import com.solmaz.dto.response.PostResponse;
import com.solmaz.exception.PinIsNotCorrectException;
import com.solmaz.service.PrivatePostService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashSet;

@RestController
@RequestMapping("api/v1/PrivatePost")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class PrivatePostController {
    private final PrivatePostService privatePostService;
    private final TokenProvider tokenProvider;
    @PostMapping("/sendPrivatePost")
    public PostReceiverResponse sendPrivatePost(@RequestBody AddPrivatePostRequest addPrivatePostRequest){
        final var userId = tokenProvider.getUserIdFromRequest();
        return privatePostService.sendPrivatePost(userId,addPrivatePostRequest);
    }
    @PostMapping("/getReceivedPrivatePosts")
    public HashSet<PostResponse> getPrivatePosts(String pin){
        final var userId = tokenProvider.getUserIdFromRequest();
        return privatePostService.getUserPrivatePosts(userId,pin);
    }
    @ExceptionHandler(PinIsNotCorrectException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handlePinException(PinIsNotCorrectException pinIsNotCorrectException){
        return new ErrorResponse(pinIsNotCorrectException.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }
}
