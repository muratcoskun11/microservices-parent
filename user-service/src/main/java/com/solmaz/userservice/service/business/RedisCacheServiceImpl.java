package com.solmaz.userservice.service.business;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl {

    private final RestTemplate restTemplate;
    public void removeToken(String userId) {
        var response = restTemplate.exchange("http://cache-service/cache/redis/token", HttpMethod.DELETE, new HttpEntity<>(userId), void.class);
    }
}
