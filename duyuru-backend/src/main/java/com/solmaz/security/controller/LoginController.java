package com.solmaz.security.controller;

import com.solmaz.dto.request.LoginRequest;
import com.solmaz.exception.ErrorResponse;
import com.solmaz.dto.response.LoginResponse;
import com.solmaz.security.service.TokenProvider;
import com.solmaz.service.business.RedisCacheServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;


@RestController
@RequestMapping("/login")
@RequestScope
@CrossOrigin
@EnableCaching
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final  UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final RedisCacheServiceImpl redisCacheService;
    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest){

        var email = loginRequest.getEmail();
        var authentication = new UsernamePasswordAuthenticationToken(email,loginRequest.getPassword());
        authenticationManager.authenticate(authentication);
        final var userId = userDetailsService.loadUserByUsername(email).getUsername();
        final var token = tokenProvider.generateToken(email);
        var response = new LoginResponse();
        if(tokenProvider.isExpired(token)) {
            redisCacheService.removeToken(email);
            response.setToken(tokenProvider.generateToken(email));
        }else {
            response.setToken(token);
        }
        response.setId(userId);
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception exception){
        return new ErrorResponse(exception.getMessage());
    }
}
