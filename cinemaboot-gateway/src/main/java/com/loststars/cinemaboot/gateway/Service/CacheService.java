package com.loststars.cinemaboot.gateway.Service;

public interface CacheService {

    Object getCache(String key);

    void setCache(String key, Object object);
}
