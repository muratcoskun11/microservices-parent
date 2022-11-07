package com.solmaz.userservice.service;

import com.solmaz.userservice.dto.AddUserRequest;
import com.solmaz.userservice.dto.UpdateUserRequest;
import com.solmaz.userservice.dto.UserResponse;
import com.solmaz.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse save(AddUserRequest addUserRequest) throws IOException;

    List<User> findAllById(List<String> idList);

    UserResponse findByEmail(String email);

    String write(MultipartFile file, String filePath) throws IOException;

    String writeBase64Bytes(String file,String filePath) throws IOException;

    String getUserPhotoBase64(String userId) throws IOException;

    UserResponse update(UpdateUserRequest updateUserRequest) throws IOException;

    void deleteById(String userId);


    User findById(String userId);
    UserResponse getUser(String userId);

    List<UserResponse> searchByUsernameStartsWith(String nameStartsWith);

    void setPin(String userId,String pin);

    List<UserResponse> findAll();

    boolean existsByEmail(String email);
}

