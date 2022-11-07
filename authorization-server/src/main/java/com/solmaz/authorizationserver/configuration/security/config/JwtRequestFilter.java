package com.solmaz.authorizationserver.configuration.security.config;

import com.solmaz.authorizationserver.configuration.security.service.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try{
            var token = tokenProvider.extractJwtFromRequest(request);
            if(token == null){
                filterChain.doFilter(request,response);
            return;
            }
            if(StringUtils.hasText(token)&&tokenProvider.validate(token)){
                String principal = tokenProvider.getUserIdFromToken(token);
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal,null,new ArrayList<>());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request,response);
                return;
            }else{
                if(isBasicAuthRequest(request)){
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(request,response);
                    return;
                }
                prepareInvalidAuthResponse(response);
                return;
            }
        }catch (ExpiredJwtException | BadCredentialsException ex) {
            prepareInvalidAuthResponse(response);
            return;
        }
        catch(Exception ex) {
            prepareInvalidAuthResponse(response);
            return;
        }
    }
    private boolean isBasicAuthRequest(HttpServletRequest request) {
        String data = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (StringUtils.hasText(data) && data.startsWith("Basic "));
    }

    private void prepareInvalidAuthResponse(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("INVALID-AUTH", "Invalid authentication attempt!");
    }
}
