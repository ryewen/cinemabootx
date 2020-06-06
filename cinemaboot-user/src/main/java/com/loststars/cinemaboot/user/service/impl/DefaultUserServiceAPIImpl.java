package com.loststars.cinemaboot.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loststars.cinemaboot.api.user.UserServiceAPI;
import com.loststars.cinemaboot.api.user.model.UserModel;
import com.loststars.cinemaboot.user.dao.UserDOMapper;
import com.loststars.cinemaboot.user.dao.WalletLogDOMapper;
import com.loststars.cinemaboot.user.dataobject.UserDO;
import com.loststars.cinemaboot.user.dataobject.UserDOExample;
import com.loststars.cinemaboot.user.dataobject.WalletLogDO;
import com.loststars.cinemaboot.user.dataobject.WalletLogDOExample;
import com.loststars.cinemaboot.user.service.model.WalletLogModel;


import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class DefaultUserServiceAPIImpl implements UserServiceAPI {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private WalletLogDOMapper walletLogDOMapper;

    private static Logger logger = LoggerFactory.getLogger(DefaultUserServiceAPIImpl.class);

    @Override
    public UserModel getUserModelByUsername(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UserDO> userDOs = userDOMapper.selectByExample(example);

        if (userDOs.isEmpty()) return null;

        UserDO userDO = userDOs.get(0);

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);

        return userModel;
    }

    @Override
    public UserModel getUserModelByTelephone(String telephone) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andTelephoneEqualTo(telephone);
        List<UserDO> userDOs = userDOMapper.selectByExample(example);

        if (userDOs.isEmpty()) return null;

        UserDO userDO = userDOs.get(0);

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);

        return userModel;
    }

    @Override
    public boolean createUser(UserModel userModel) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);

        int result = userDOMapper.insertSelective(userDO);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BigDecimal getWalletByUserId(Integer userId) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) return null;
        return userDO.getWallet();
    }

    @Override
    @Transactional
    public boolean record(BusinessActionContext actionContext, String orderId, Integer userId, BigDecimal cost) {
        logger.info("DefaultUserServiceAPIImpl record " + orderId);
        Date start = new Date();
        //插入一条钱包流水
        WalletLogModel walletLogModel = new WalletLogModel();
        walletLogModel.setWallet(cost);
        walletLogModel.setUserId(userId);
        walletLogModel.setStatus(WalletLogModel.STATUS_DRAFT);
        walletLogModel.setOrderId(orderId);

        WalletLogDO walletLogDO = new WalletLogDO();
        BeanUtils.copyProperties(walletLogModel, walletLogDO);

        int result = walletLogDOMapper.insertSelective(walletLogDO);

        //插入成功则扣减钱包
        if (result > 0) {
            result = userDOMapper.reduceWallet(userId, cost);
            if (result == 0) {
                throw new ArithmeticException("钱包余额不足");
            }
        } else {
            throw new ArithmeticException("钱包流水插入失败");
        }
        Date end = new Date();
        logger.info("DefaultUserServiceAPIImpl record spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultUserServiceAPIImpl commit " + orderId);
        Date start = new Date();
        //根据orderid查询钱包流水
        WalletLogDOExample example = new WalletLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<WalletLogDO> walletLogDOs = walletLogDOMapper.selectByExample(example);

        if (walletLogDOs.isEmpty()) return true;
        WalletLogDO walletLogDO = walletLogDOs.get(0);

        //将状态为draft的流水状态更新为confirmed
        if (walletLogDO.getStatus().equals(WalletLogModel.STATUS_DRAFT)) {
            walletLogDOMapper.updateStatus(walletLogDO.getId(), WalletLogModel.STATUS_DRAFT, WalletLogModel.STATUS_CONFIRMED);
        }
        Date end = new Date();
        logger.info("DefaultUserServiceAPIImpl commit spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultUserServiceAPIImpl rollback " + orderId);
        Integer userId = (Integer) actionContext.getActionContext("userId");
        BigDecimal cost = (BigDecimal) actionContext.getActionContext("cost");
        Date start = new Date();
        //根据orderid查询钱包流水
        WalletLogDOExample example = new WalletLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<WalletLogDO> walletLogDOs = walletLogDOMapper.selectByExample(example);

        if (walletLogDOs.isEmpty()) {
            WalletLogModel walletLogModel = new WalletLogModel();
            walletLogModel.setWallet(cost);
            walletLogModel.setUserId(userId);
            walletLogModel.setStatus(WalletLogModel.STATUS_CANCEL);
            walletLogModel.setOrderId(orderId);

            WalletLogDO walletLogDO = new WalletLogDO();
            BeanUtils.copyProperties(walletLogModel, walletLogDO);

            int result = walletLogDOMapper.insertSelective(walletLogDO);

            if (result == 0) throw new ArithmeticException("钱包流水插入失败");

            return true;
        }
        WalletLogDO walletLogDO = walletLogDOs.get(0);

        //将状态为draft的流水状态更新为confirmed，并回补
        if (walletLogDO.getStatus().equals(WalletLogModel.STATUS_DRAFT)) {
            walletLogDOMapper.updateStatus(walletLogDO.getId(), WalletLogModel.STATUS_DRAFT, WalletLogModel.STATUS_CANCEL);
            userDOMapper.addWallet(userId, cost);
        }
        Date end = new Date();
        logger.info("DefaultUserServiceAPIImpl rollback spend time " + (end.getTime() - start.getTime()));
        return true;
    }
}
