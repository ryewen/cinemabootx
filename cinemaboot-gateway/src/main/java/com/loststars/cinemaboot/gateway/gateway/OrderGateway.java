package com.loststars.cinemaboot.gateway.gateway;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.field.FieldServiceAPI;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.order.OrderServiceAPI;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import com.loststars.cinemaboot.gateway.controller.OrderController;

import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderGateway {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    @Autowired
    private FieldServiceAPI fieldServiceAPI;

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Autowired
    private MovieServiceAPI movieServiceAPI;

    private static Logger logger = LoggerFactory.getLogger(OrderGateway.class);

    @GlobalTransactional
    public boolean record(String orderId, Integer movieId, Integer sale, Integer userId, BigDecimal cost, Integer fieldId, String seatName, Integer seatId) {
        logger.info("OrderGateway record " + orderId);

        orderServiceAPI.record(null, orderId);

        fieldServiceAPI.record(null, orderId, fieldId, seatName, seatId);

        userServiceAPI.record(null, orderId, userId, cost);

        movieServiceAPI.record(null, orderId, movieId, sale);

        return true;
    }
}
