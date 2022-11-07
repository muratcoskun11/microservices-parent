package com.solmaz.controller;

import com.solmaz.dto.request.UpdateUserRequest;
import com.solmaz.dto.response.ErrorResponse;
import com.solmaz.dto.response.UserResponse;
import com.solmaz.service.UserService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    /*@PostMapping(value = "/createUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> save(@RequestBody AddUserRequest addUserRequest) throws IOException {
        return ResponseEntity.ok(userService.save(addUserRequest));
    }*/
    @PutMapping("/editPhoto")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> update(@RequestParam String base64Photo) throws IOException {
        final var userId = tokenProvider.getUserIdFromRequest();
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
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }
}
