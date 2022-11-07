package com.solmaz.authorizationserver.configuration.security.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String generateToken(String username);
    public boolean validate(String token);
    String getUserIdFromToken(String token);
    String extractJwtFromRequest(HttpServletRequest httpServletRequest);

    String getUserIdFromRequest();

    boolean isExpired(String token);
}
