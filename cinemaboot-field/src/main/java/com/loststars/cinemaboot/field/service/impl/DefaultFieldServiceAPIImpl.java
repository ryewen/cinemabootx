package com.loststars.cinemaboot.field.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.loststars.cinemaboot.api.field.FieldServiceAPI;
import com.loststars.cinemaboot.api.field.model.FieldModel;
import com.loststars.cinemaboot.api.field.model.SeatModel;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.movie.model.MovieModel;
import com.loststars.cinemaboot.field.dao.FieldDOMapper;
import com.loststars.cinemaboot.field.dao.SeatDOMapper;
import com.loststars.cinemaboot.field.dao.SeatLogDOMapper;
import com.loststars.cinemaboot.field.dataobject.*;
import com.loststars.cinemaboot.field.service.model.SeatLogModel;


import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

@Service(interfaceClass = FieldServiceAPI.class)
public class DefaultFieldServiceAPIImpl implements FieldServiceAPI {

    @Autowired
    private FieldDOMapper fieldDOMapper;

    @Autowired
    private SeatDOMapper seatDOMapper;

    @Reference(interfaceClass = MovieServiceAPI.class)
    private MovieServiceAPI movieServiceAPI;

    @Autowired
    private SeatLogDOMapper seatLogDOMapper;

    private static Logger logger = LoggerFactory.getLogger(DefaultFieldServiceAPIImpl.class);

    @Override
    public List<FieldModel> listFieldModelsByMovieId(Integer movieId) {
        //根据movieId查询对应的field
        FieldDOExample example = new FieldDOExample();
        example.createCriteria().andMovieIdEqualTo(movieId);

        List<FieldDO> fieldDOs = fieldDOMapper.selectByExample(example);

        //根据fieldId查询对应的seat
        return fieldDOs.stream().map((fieldDO) -> {
            FieldModel fieldModel = new FieldModel();
            BeanUtils.copyProperties(fieldDO, fieldModel);

            SeatDOExample seatDOExample = new SeatDOExample();
            seatDOExample.createCriteria().andFieldIdEqualTo(fieldDO.getId());
            List<SeatDO> seatDOs = seatDOMapper.selectByExample(seatDOExample);

            List<SeatModel> seatModels = seatDOs.stream().map((seatDO) -> {
                SeatModel seatModel = new SeatModel();
                BeanUtils.copyProperties(seatDO, seatModel);
                return seatModel;
            }).collect(Collectors.toList());
            fieldModel.setSeatModels(seatModels);
            return fieldModel;
        }).collect(Collectors.toList());
    }

    @Override
    public FieldModel getFieldModelById(Integer fieldId) {
        //根据fieldId查询对应场次信息
        FieldDO fieldDO = fieldDOMapper.selectByPrimaryKey(fieldId);
        if (fieldDO == null) return null;

        FieldModel fieldModel = new FieldModel();
        BeanUtils.copyProperties(fieldDO, fieldModel);

        //根据fieldId查询对应座位信息
        SeatDOExample seatDOExample = new SeatDOExample();
        seatDOExample.createCriteria().andFieldIdEqualTo(fieldId);
        List<SeatDO> seatDOs = seatDOMapper.selectByExample(seatDOExample);

        List<SeatModel> seatModels = seatDOs.stream().map((seatDO) -> {
            SeatModel seatModel = new SeatModel();
            BeanUtils.copyProperties(seatDO, seatModel);
            return seatModel;
        }).collect(Collectors.toList());
        fieldModel.setSeatModels(seatModels);

        //查询影片信息
        MovieModel movieModel = movieServiceAPI.getMovieModelById(fieldDO.getMovieId());
        fieldModel.setMovieModel(movieModel);

        return fieldModel;
    }

    @Override
    @Transactional
    public boolean record(BusinessActionContext actionContext, String orderId, Integer fieldId, String seatName, Integer seatId) {
        logger.info("DefaultFieldServiceAPIImpl record " + orderId);
        Date start = new Date();
        //插入座位流水
        SeatLogModel seatLogModel = new SeatLogModel();
        seatLogModel.setFieldId(fieldId);
        seatLogModel.setStatus(SeatLogModel.STATUS_DRAFT);
        seatLogModel.setSeatName(seatName);
        seatLogModel.setOrderId(orderId);

        SeatLogDO seatLogDO = new SeatLogDO();
        BeanUtils.copyProperties(seatLogModel, seatLogDO);

        int result = seatLogDOMapper.insertSelective(seatLogDO);

        //若插入结果为0，或产生唯一键冲突，则抛出异常，可以防悬挂
        if (result == 0) throw new ArithmeticException("座位流水插入失败");

        //将座位状态更新为已售出
        SeatDOExample example = new SeatDOExample();
        example.createCriteria().andIdEqualTo(seatId);
        List<SeatDO> seatDOs = seatDOMapper.selectByExample(example);
        if (seatDOs.isEmpty()) throw new ArithmeticException("座位不存在");
        SeatDO seatDO = seatDOs.get(0);

        if (seatDO.getStatus() == SeatModel.STATUS_SALED) throw new ArithmeticException("座位已售出");

        result = seatDOMapper.sellSeat(seatDO);

        Date end = new Date();
        logger.info("DefaultFieldServiceAPIImpl record spend time " + (end.getTime() - start.getTime()));

        if (result == 0) throw new ArithmeticException("座位状态更新失败");
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultFieldServiceAPIImpl commit " + orderId);
        Date start = new Date();
        //根据orderid获取座位流水
        SeatLogDOExample example = new SeatLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<SeatLogDO> seatLogDOs = seatLogDOMapper.selectByExample(example);
        if (seatLogDOs.isEmpty()) return true;
        SeatLogDO seatLogDO = seatLogDOs.get(0);

        //将状态为draft的流水状态更新为confirmed
        if (seatLogDO.getStatus().equals(SeatLogModel.STATUS_DRAFT)) {
            seatLogDOMapper.updateStatus(seatLogDO.getId(), SeatLogModel.STATUS_DRAFT, SeatLogModel.STATUS_CONFIRMED);
        }
        Date end = new Date();
        logger.info("DefaultFieldServiceAPIImpl commit spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultFieldServiceAPIImpl rollback " + orderId);
        Integer seatId = (Integer) actionContext.getActionContext("seatId");
        String seatName = (String) actionContext.getActionContext("seatName");
        Integer fieldId = (Integer) actionContext.getActionContext("fieldId");
        Date start = new Date();
        //根据orderid获取座位流水
        SeatLogDOExample example = new SeatLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<SeatLogDO> seatLogDOs = seatLogDOMapper.selectByExample(example);
        if (seatLogDOs.isEmpty()) {
            //防止空回滚，直接返回即可
            //防止悬挂，需要插入一条状态为cancel的流水，并将座位状态设为未售，由于空回滚、悬挂时，record都未更改座位状态，所以也不用更改座位状态
            SeatLogModel seatLogModel = new SeatLogModel();
            seatLogModel.setStatus(SeatLogModel.STATUS_CANCEL);
            seatLogModel.setSeatName(seatName);
            seatLogModel.setOrderId(orderId);
            seatLogModel.setFieldId(fieldId);

            SeatLogDO seatLogDO = new SeatLogDO();
            BeanUtils.copyProperties(seatLogModel, seatLogDO);

            int result = seatLogDOMapper.insertSelective(seatLogDO);

            if (result == 0) throw new ArithmeticException("座位流水插入失败");

            return true;
        }
        SeatLogDO seatLogDO = seatLogDOs.get(0);

        //将状态为draft的流水状态更新为cancel，并回补座位状态
        if (seatLogDO.getStatus().equals(SeatLogModel.STATUS_DRAFT)) {
            int result = seatLogDOMapper.updateStatus(seatLogDO.getId(), SeatLogModel.STATUS_DRAFT, SeatLogModel.STATUS_CANCEL);
            if (result == 0) return true;
            SeatDOExample seatDOExample = new SeatDOExample();
            seatDOExample.createCriteria().andIdEqualTo(seatId);
            List<SeatDO> seatDOs = seatDOMapper.selectByExample(seatDOExample);
            SeatDO seatDO = seatDOs.get(0);
            seatDOMapper.saveSeat(seatDO);
        }
        Date end = new Date();
        logger.info("DefaultFieldServiceAPIImpl rollback spend time " + (end.getTime() - start.getTime()));
        return true;
    }
}
