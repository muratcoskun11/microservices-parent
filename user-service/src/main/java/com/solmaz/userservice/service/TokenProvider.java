package com.solmaz.loginservice.service;

import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String generateToken(String username);
    public boolean validate(String token);
    String getUserIdFromToken(String token);
    String extractJwtFromRequest(HttpServletRequest request);
    boolean isExpired(String token);
}
