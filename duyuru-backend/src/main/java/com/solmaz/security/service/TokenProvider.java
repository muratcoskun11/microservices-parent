package com.solmaz.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String generateToken(String username);
    public boolean validate(String token);
    String getUserIdFromToken(String token);
    String extractJwtFromRequest(HttpServletRequest httpServletRequest);

    String getUserIdFromRequest();

    boolean isExpired(String token);
}
