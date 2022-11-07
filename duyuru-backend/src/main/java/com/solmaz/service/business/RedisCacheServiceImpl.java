package com.solmaz.service.business;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
@EnableCaching
@Service
public class RedisCacheServiceImpl {
    @CacheEvict(key = "#email",value = "usertoken")
    public void removeToken(String email) {
    }
}
