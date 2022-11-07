package com.solmaz.userservice.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String generateToken(String username);
    public boolean validate(String token);
    String getUserIdFromToken(String token);
    String extractJwtFromRequest(HttpServletRequest request);
    boolean isExpired(String token);

    String getUserIdFromRequest();
}
