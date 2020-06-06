package com.loststars.cinemaboot.gateway.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.loststars.cinemaboot.api.field.FieldServiceAPI;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.order.OrderServiceAPI;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboBeanConfig {

    @Bean
    public ReferenceBean<FieldServiceAPI> fieldServiceAPI() {
        ReferenceBean<FieldServiceAPI> ref = new ReferenceBean<>();
        ref.setInterface(FieldServiceAPI.class);
        return ref;
    }

    @Bean
    public ReferenceBean<MovieServiceAPI> movieServiceAPI() {
        ReferenceBean<MovieServiceAPI> ref = new ReferenceBean<>();
        ref.setInterface(MovieServiceAPI.class);
        return ref;
    }

    @Bean
    public ReferenceBean<OrderServiceAPI> orderServiceAPI() {
        ReferenceBean<OrderServiceAPI> ref = new ReferenceBean<>();
        ref.setInterface(OrderServiceAPI.class);
        return ref;
    }

    @Bean
    public ReferenceBean<UserServiceAPI> userServiceAPI() {
        ReferenceBean<UserServiceAPI> ref = new ReferenceBean<>();
        ref.setInterface(UserServiceAPI.class);
        return ref;
    }
}

