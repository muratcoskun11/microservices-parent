package com.solmaz.userservice.config;

import com.solmaz.userservice.dto.UserResponse;
import com.solmaz.userservice.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {




    private static final Converter<User, UserResponse> userUserResponseConverter = context -> {
        var user = context.getSource();
        var userResponse = UserResponse.builder().userId(user.getUserId()).title(user.getTitle()).fullName(user.getFullName())
                .photoUrl(user.getPhotoUrl()).build();
        return userResponse;
    };





    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.addConverter(userUserResponseConverter, User.class, UserResponse.class);
        return modelMapper;

    }
}
