package com.solmaz.userservice.service.business;

import com.google.common.hash.Hashing;
import com.solmaz.userservice.dto.AddUserRequest;
import com.solmaz.userservice.dto.UpdateUserRequest;
import com.solmaz.userservice.dto.UserResponse;
import com.solmaz.userservice.entity.User;
import com.solmaz.userservice.exception.NotFoundException;
import com.solmaz.userservice.repository.UserRepository;
import com.solmaz.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RedisCacheServiceImpl redisCacheService;

    private static NotFoundException userNotFound() {
        return new NotFoundException("User not found!");
    }
    @Value("${user.images.directory}")
    private String userImagesDirectory;
    @Override
    public UserResponse save(AddUserRequest addUserRequest) throws IOException {

        var user = userRepository.save(modelMapper.map(addUserRequest, User.class));
        var rootPath = System.getProperty("user.dir")+userImagesDirectory;
        var userPath = rootPath+"/"+user.getUserId();
        //for multipartfile request
        //var photoUrl = write(addUserRequest.getMultipartFile(), user.getUserId()+addUserRequest.getMultipartFile().getOriginalFilename());
        var photoUrl = writeBase64Bytes(addUserRequest.getPhoto(),userPath);
        user.setPhotoUrl(photoUrl);
        var savedUser =userRepository.save(user);
        System.err.println(addUserRequest.getFullname());
        return modelMapper.map(savedUser,UserResponse.class);
    }

    @Override
    public List<User> findAllById(List<String> idList) {
        return userRepository.findAllById(idList);
    }

    @Override
    public UserResponse findByEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email).orElseThrow(UserServiceImpl::userNotFound),UserResponse.class);
    }

    @Override
    public String write(MultipartFile file, String filePath) throws IOException {
        String fullPath = System.getProperty("user.dir")+userImagesDirectory+"/"+filePath;
        Path path = Paths.get(fullPath);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }
    @Override
    public String writeBase64Bytes(String file,String filePath) throws IOException {
        byte[] content = Base64.getDecoder().decode(file);
        InputStream is = new ByteArrayInputStream(content);
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        is.close();
        var extension = "";
        System.err.println(mimeType);
        if(mimeType==null||!mimeType.startsWith("image/"))
            throw new IllegalArgumentException("It is not an image");
        for (int i=0;i<mimeType.length();i++){
            if(mimeType.charAt(i)=='/') {
                extension = mimeType.substring(i+1);
                break;
            }
        }
        var fullPath = filePath+"."+extension;
        Path path = Paths.get(fullPath);
        Files.createDirectories(path.getParent());
        System.err.println(extension);
        Files.write(path,content, StandardOpenOption.CREATE);
        return path.toString();
    }
    @Override
    public String getUserPhotoBase64(String userId) throws IOException {
        var photoUrl = userRepository.getPhotoUrlById(userId).orElseThrow(()->new IllegalArgumentException("user id or photo url null "));
        var bytes = Files.readAllBytes(Paths.get(photoUrl));
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public UserResponse update(UpdateUserRequest updateUserRequest) throws IOException {
        var user = findById(updateUserRequest.getUserId());
        var rootPath = System.getProperty("user.dir")+userImagesDirectory;
        var userPath = rootPath+"/"+user.getUserId();
        //for multipartfile request
        //var photoUrl = write(addUserRequest.getMultipartFile(), user.getUserId()+addUserRequest.getMultipartFile().getOriginalFilename());
        var photoUrl = writeBase64Bytes(updateUserRequest.getBase64Photo(),userPath);
        user.setPhotoUrl(photoUrl);
        var updatedUser = userRepository.saveAndFlush(user);
        return modelMapper.map(updatedUser,UserResponse.class);
    }

    @Override
    public void deleteById(String userId) {
        redisCacheService.removeToken(userId);
        userRepository.deleteById(userId);
    }


    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(UserServiceImpl::userNotFound);
    }
    @Override
    public UserResponse getUser(String userId) {
        return modelMapper.map(userRepository.findById(userId).orElseThrow(UserServiceImpl::userNotFound),UserResponse.class);
    }

    @Override
    public List<UserResponse> searchByUsernameStartsWith(String nameStartsWith) {
        return userRepository.findAllByFullNameStartsWithIgnoreCase(nameStartsWith).stream().map(user -> modelMapper.map(user,UserResponse.class)).toList();
    }
    @Override
    public void setPin(String userId,String pin) {
        String hashedPin = Hashing.sha256().hashString(pin, StandardCharsets.UTF_8).toString();
        var user = findById(userId);
        user.setPrivateMessagePin(hashedPin);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user,UserResponse.class)).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
