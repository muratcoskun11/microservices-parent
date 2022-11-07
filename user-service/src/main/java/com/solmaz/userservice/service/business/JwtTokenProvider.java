package com.solmaz.loginservice.service.business;

import com.solmaz.loginservice.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {
    private final RestTemplate restTemplate;
    @Override
    public String generateToken(String userId) {
        Map<String,String> params = new HashMap<>();
        var url = UriComponentsBuilder.fromHttpUrl("http://token-service/token/generate").queryParam("userId","{userId}").encode().toUriString();
        params.put("userId",userId);
        var response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),String.class,params);
        return response.getBody();
    }

    @Override
    public boolean validate(String token) {
        var response = restTemplate.exchange("http://token-service/token/validate", HttpMethod.GET, new HttpEntity<>(token),Boolean.class);
        return response.getBody();
    }

    @Override
    public String getUserIdFromToken(String token) {
        var response = restTemplate.exchange("http://token-service/token/getUserId", HttpMethod.GET, new HttpEntity<>(token),String.class);
        return response.getBody();
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        String data = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(data) && data.startsWith("Bearer ")) {
            return data.substring(7);
        }
        return null;
    }

    @Override
    public boolean isExpired(String token){
        var response = restTemplate.exchange("http://token-service/token/isExpired", HttpMethod.GET, new HttpEntity<>(token),boolean.class);
        return response.getBody();
    }

}
