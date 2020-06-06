package com.loststars.cinemaboot.gateway.gateway;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import com.loststars.cinemaboot.api.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class UserGateway {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UserServiceAPI userServiceAPI;

    public UserModel getUserModelByTelephone(String telephone) {
        String key = "UserModel-Telephone-" + telephone;
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(key);
        if (userModel != null) return userModel;
        userModel = userServiceAPI.getUserModelByTelephone(telephone);
        if (userModel != null) {
            redisTemplate.opsForValue().set(key, userModel);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return userModel;
    }

    public UserModel getUserModelByUsername(String username) {
        String key = "UserModel-Username-" + username;
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(key);
        if (userModel != null) return userModel;
        userModel = userServiceAPI.getUserModelByUsername(username);
        if (userModel != null) {
            redisTemplate.opsForValue().set(key, userModel);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return userModel;
    }

    public BigDecimal getWalletByUserId(Integer userId) {
        String key = "Wallet-" + userId;
        //BigDecimal wallet = (BigDecimal.valueOf((double) redisTemplate.opsForValue().get(key)));
        Object val = redisTemplate.opsForValue().get(key);
        Double wallet = null;
        if (val != null) {
            if (val instanceof Double) {
                wallet = (Double) val;
            } else {
                wallet = Double.valueOf((Integer) val);
            }
            return BigDecimal.valueOf(wallet);
        }
        BigDecimal walletInDatabase = userServiceAPI.getWalletByUserId(userId);
        if (walletInDatabase != null) {
            wallet = walletInDatabase.doubleValue();
            redisTemplate.opsForValue().set(key, wallet);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return walletInDatabase;
    }

    public UserModel getUserModelByToken(String token) {
        String tokenKey = "Token-" + token;
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(tokenKey);
        return userModel;
    }

}
