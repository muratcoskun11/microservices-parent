package com.solmaz.userservice.controller;

import com.solmaz.userservice.dto.AddUserRequest;
import com.solmaz.userservice.dto.UpdateUserRequest;
import com.solmaz.userservice.dto.UserResponse;
import com.solmaz.userservice.exception.ErrorResponse;
import com.solmaz.userservice.service.TokenProvider;
import com.solmaz.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequestScope
@CrossOrigin
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    // rootURL= localhost:7070/duyuru/api/v1/user


    @PostMapping(value = "/createUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> save(@RequestBody AddUserRequest addUserRequest) throws IOException {
        return ResponseEntity.ok(userService.save(addUserRequest));
    }
    @PutMapping("/editPhoto")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> update(@RequestParam String base64Photo) throws IOException {
        var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(userService.update(new UpdateUserRequest(userId,base64Photo)));
    }
    @PostMapping("/getUserPhoto")
    public String getUserPhotoBase64() throws IOException {
        final var userId = tokenProvider.getUserIdFromRequest();
        return userService.getUserPhotoBase64(userId);
    }
    /*@PostMapping(value ="/writeBase64AsBytes")
    public String writeBase64Bytes(@RequestBody PhotoRequest photoRequest) throws IOException {
        return userService.writeBase64Bytes(photoRequest.getFile(), photoRequest.;
    }*/
    @DeleteMapping("/removeUser")
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        final var userId = tokenProvider.getUserIdFromRequest();
        userService.deleteById(userId);
    }

    @GetMapping("/getUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> findByUserId() {
        final var userId = tokenProvider.getUserIdFromRequest();
        return ResponseEntity.ok(userService.getUser(userId));
    }
    @PutMapping("/setPin")
    @ResponseStatus(HttpStatus.OK)
    public void setPin(@RequestParam String pin){
        final var userId = tokenProvider.getUserIdFromRequest();
        userService.setPin(userId,pin);
    }
    @GetMapping("/getAllUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/existsByEmail")
    @ResponseStatus(HttpStatus.OK)
    public boolean existsByEmail(@RequestParam String email){
        return userService.existsByEmail(email);
    }
    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Jwt principal){
        System.err.println(principal.getSubject());
        System.err.println(principal.getClaims().toString());
        System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.err.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
        System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.err.println("hello");
        return "hello";
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }



}
