package com.loststars.cinemaboot.gateway.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import com.loststars.cinemaboot.api.user.model.UserModel;
import com.loststars.cinemaboot.gateway.controller.vo.ResponseVO;
import com.loststars.cinemaboot.gateway.exception.BusinessException;
import com.loststars.cinemaboot.gateway.exception.EmBusinessException;
import com.loststars.cinemaboot.gateway.gateway.UserGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Autowired
    private UserGateway userGateway;

    @PostMapping("/otpCode")
    public ResponseVO getOtpCode(@RequestParam("telephone") String telephone) {
        //参数校验
        if (StringUtils.isEmpty(telephone)) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "手机号不能为空");

        //生成短信验证码，并写入Redis
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; ++ i) {
            builder.append(random.nextInt(9) + 1);
        }
        String otpCode = builder.toString();
        System.out.println("telephone=" + telephone + " otpCode=" + otpCode);

        String key = "OtpCode-" + telephone;
        redisTemplate.opsForValue().set(key, otpCode);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);

        return ResponseVO.success(null);
    }

    @PostMapping("/register")
    public ResponseVO register(@RequestParam("telephone") String telephone, @RequestParam("otpCode") String otpCode,
                               @RequestParam("username") String username, @RequestParam("password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //参数校验
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(otpCode) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION);
        }

        //从Redis中取出验证码，进行校验
        String key = "OtpCode-" + telephone;
        String optCodeRedis = (String) redisTemplate.opsForValue().get(key);

        if (! StringUtils.equals(otpCode, optCodeRedis)) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "短信验证码错误");

        //判断用户名是否已存在，手机号是否已存在
        if (userGateway.getUserModelByUsername(username) != null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "用户名已存在");

        if (userGateway.getUserModelByTelephone(telephone) != null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "手机号已注册");


        //将密码md5后，把用户插入数据库
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setTelephone(telephone);
        userModel.setPassword(encodeByMD5(password));

        boolean result = userServiceAPI.createUser(userModel);
        key = "UserModel-Username-" + username;
        redisTemplate.opsForValue().set(key, userModel);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        key = "UserModel-Telephone-" + telephone;
        redisTemplate.opsForValue().set(key, userModel);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);

        if (result) {
            return ResponseVO.success(null);
        } else {
            throw new BusinessException(EmBusinessException.UNKNOW_EXCEPTION, "用户创建失败，请稍后重试");
        }
    }

    @PostMapping("/login")
    public ResponseVO login(@RequestParam("telephone") String telephone, @RequestParam("password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //参数校验
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION);

        //根据手机号查询用户
        UserModel userModel = userGateway.getUserModelByTelephone(telephone);

        if (userModel == null) throw new BusinessException(EmBusinessException.USER_NOEXIST);

        //校验密码是否相等
        String passwordMD5 = encodeByMD5(password);
        if (! StringUtils.equals(passwordMD5, userModel.getPassword())) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "用户名或密码错误");

        //生成token，存入redis
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = "Token-" + token;
        redisTemplate.opsForValue().set(key, userModel);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);

        return ResponseVO.success(token);
    }

    private String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) return null;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }
}
