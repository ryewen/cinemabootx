package com.loststars.cinemaboot.gateway.Service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.loststars.cinemaboot.gateway.Service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String, Object> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public Object getCache(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void setCache(String key, Object object) {
        cache.put(key, object);
    }
}
