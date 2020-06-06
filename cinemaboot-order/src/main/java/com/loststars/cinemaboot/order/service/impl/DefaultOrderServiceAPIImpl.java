package com.loststars.cinemaboot.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loststars.cinemaboot.api.order.OrderServiceAPI;
import com.loststars.cinemaboot.api.order.model.OrderModel;
import com.loststars.cinemaboot.order.dao.OrderDOMapper;
import com.loststars.cinemaboot.order.dataobject.OrderDO;
import com.loststars.cinemaboot.order.dataobject.OrderDOExample;
import io.seata.rm.tcc.api.BusinessActionContext;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class DefaultOrderServiceAPIImpl implements OrderServiceAPI {

    @Autowired
    private OrderDOMapper orderDOMapper;

    private static Logger logger = LoggerFactory.getLogger(DefaultOrderServiceAPIImpl.class);

    @Override
    @Transactional
    public boolean createOrder(String orderId, Integer userId, Integer movieId, Integer fieldId, String seatName, BigDecimal cost, Date createTime) {
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setStatus(OrderModel.STATUS_DRAFT);
        orderModel.setSeatName(seatName);
        orderModel.setMovieId(movieId);
        orderModel.setId(orderId);
        orderModel.setCost(cost);

        orderModel.setFieldId(fieldId);
        orderModel.setCreateTime(createTime);

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setCreateTime(new Timestamp(orderModel.getCreateTime().getTime()));
        System.out.println(orderDO.getCreateTime().toString());
        int result = orderDOMapper.insertSelective(orderDO);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean record(BusinessActionContext actionContext, String orderId) {
        logger.info("DefaultOrderServiceAPIImpl record " + orderId);
        Date start = new Date();
        //将draft的订单状态更新为paying
        int result = orderDOMapper.updateStatus(orderId, OrderModel.STATUS_DRAFT, OrderModel.STATUS_PAYING);

        Date end = new Date();
        logger.info("DefaultOrderServiceAPIImpl record spend time " + (end.getTime() - start.getTime()));
        //若失败则抛出异常
        if (result == 0) {
            throw new ArithmeticException("订单状态更新失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultOrderServiceAPIImpl commit " + orderId);
        Date start = new Date();
        OrderDOExample example = new OrderDOExample();
        example.createCriteria().andIdEqualTo(orderId);
        List<OrderDO> orderDOs = orderDOMapper.selectByExample(example);

        if (orderDOs.isEmpty()) return true;
        OrderDO orderDO = orderDOs.get(0);

        //将paying的订单状态更新为confirmed
        if (orderDO.getStatus().equals(OrderModel.STATUS_PAYING)) {
            orderDOMapper.updateStatus(orderId, OrderModel.STATUS_PAYING, OrderModel.STATUS_CONFIRMED);
        }
        Date end = new Date();
        logger.info("DefaultOrderServiceAPIImpl commit spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultOrderServiceAPIImpl rollback " + orderId);
        Date start = new Date();
        //将paying或draft的订单状态更新为cancel
        OrderDOExample example = new OrderDOExample();
        example.createCriteria().andIdEqualTo(orderId);
        List<OrderDO> orderDOs = orderDOMapper.selectByExample(example);

        if (orderDOs.isEmpty()) {
            return true;
        }
        OrderDO orderDO = orderDOs.get(0);

        if (orderDO.getStatus().equals(OrderModel.STATUS_DRAFT) || orderDO.getStatus().equals(OrderModel.STATUS_PAYING)) {
            orderDOMapper.updateStatus(orderId, orderDO.getStatus(), OrderModel.STATUS_CANCEL);
        }
        Date end = new Date();
        logger.info("DefaultOrderServiceAPIImpl rollback spend time " + (end.getTime() - start.getTime()));
        return true;
    }


}
