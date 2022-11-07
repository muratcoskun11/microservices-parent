package com.solmaz.authorizationserver.configuration.security.service.business;

import com.solmaz.authorizationserver.configuration.security.service.TokenProvider;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.expiration.duration}")
    private long expirationDuration;
    @Cacheable(key = "#username",value = "usertoken")
    @Override
    public String generateToken(String username) {
        var user = userDetailsService.loadUserByUsername(username);
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationDuration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
        catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    @Override
    public String getUserIdFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest httpServletRequest) {
        String data = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(data) && data.startsWith("Bearer ")) {
            return data.substring(7);
        }
        return null;
    }

    @Override
    public String getUserIdFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var token = extractJwtFromRequest(request);
        var userId = getUserIdFromToken(token);
        return userId;
    }

    @Override
    public boolean isExpired(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return false;
        }
        catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            return true;
        }
        catch (ExpiredJwtException ex) {
            return true;
        }
    }

}
