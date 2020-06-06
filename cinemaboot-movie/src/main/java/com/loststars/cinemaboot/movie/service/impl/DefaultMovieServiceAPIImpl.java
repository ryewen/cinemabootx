package com.loststars.cinemaboot.movie.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loststars.cinemaboot.api.field.model.SeatModel;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.movie.model.MovieModel;
import com.loststars.cinemaboot.movie.dao.MovieDOMapper;
import com.loststars.cinemaboot.movie.dao.SalesLogDOMapper;
import com.loststars.cinemaboot.movie.dataobject.MovieDO;
import com.loststars.cinemaboot.movie.dataobject.SalesLogDO;
import com.loststars.cinemaboot.movie.dataobject.SalesLogDOExample;
import com.loststars.cinemaboot.movie.service.model.SalesLogModel;


import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMovieServiceAPIImpl implements MovieServiceAPI {

    @Autowired
    private MovieDOMapper movieDOMapper;

    @Autowired
    private SalesLogDOMapper salesLogDOMapper;

    private static Logger logger = LoggerFactory.getLogger(DefaultMovieServiceAPIImpl.class);

    @Override
    public List<MovieModel> listMovieModels() {
        List<MovieDO> movieDOs = movieDOMapper.selectByExample(null);
        List<MovieModel> movieModels = movieDOs.stream().map((movieDO) -> {
            return convertFromMovieDO(movieDO);
        }).collect(Collectors.toList());
        return movieModels;
    }

    @Override
    public MovieModel getMovieModelById(Integer movieId) {
        MovieDO movieDO = movieDOMapper.selectByPrimaryKey(movieId);
        if (movieDO == null) return null;

        return convertFromMovieDO(movieDO);
    }

    @Override
    @Transactional
    public boolean record(BusinessActionContext actionContext, String orderId, Integer movieId, Integer sale) {
        logger.info("DefaultMovieServiceAPIImpl record " + orderId);
        Date start = new Date();
        //插入一条销量增加流水
        SalesLogModel salesLogModel = new SalesLogModel();
        salesLogModel.setStatus(SalesLogModel.STATUS_DRAFT);
        salesLogModel.setSale(sale);
        salesLogModel.setOrderId(orderId);
        salesLogModel.setMovieId(movieId);

        SalesLogDO salesLogDO = new SalesLogDO();
        BeanUtils.copyProperties(salesLogModel, salesLogDO);

        int result = salesLogDOMapper.insertSelective(salesLogDO);
        //插入成功则增加电影销量
        if (result > 0) {
            movieDOMapper.addSales(movieId, sale);
        } else {
            throw new ArithmeticException("销量流水生成失败");
        }
        Date end = new Date();
        logger.info("DefaultMovieServiceAPIImpl record spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultMovieServiceAPIImpl commit " + orderId);
        Date start = new Date();
        //查询销量流水信息
        SalesLogDOExample example = new SalesLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<SalesLogDO> salesLogDOs = salesLogDOMapper.selectByExample(example);

        if (salesLogDOs.isEmpty()) return true;
        SalesLogDO salesLogDO = salesLogDOs.get(0);

        //更新draft的流水状态为confirmed
        if (salesLogDO.getStatus().equals(SalesLogModel.STATUS_DRAFT)) {
            salesLogDOMapper.updateStatus(salesLogDO.getId(), SalesLogModel.STATUS_DRAFT, SalesLogModel.STATUS_CONFIRMED);
        }
        Date end = new Date();
        logger.info("DefaultMovieServiceAPIImpl commit spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext actionContext) {
        String orderId = (String) actionContext.getActionContext("orderId");
        logger.info("DefaultMovieServiceAPIImpl rollback " + orderId);
        Integer movieId = (Integer) actionContext.getActionContext("movieId");
        Integer sale = (Integer) actionContext.getActionContext("sale");
        Date start = new Date();
        //查询销量流水信息
        SalesLogDOExample example = new SalesLogDOExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<SalesLogDO> salesLogDOs = salesLogDOMapper.selectByExample(example);

        if (salesLogDOs.isEmpty()) {
            SalesLogModel salesLogModel = new SalesLogModel();
            salesLogModel.setStatus(SalesLogModel.STATUS_CANCEL);
            salesLogModel.setSale(sale);
            salesLogModel.setOrderId(orderId);
            salesLogModel.setMovieId(movieId);

            SalesLogDO salesLogDO = new SalesLogDO();
            BeanUtils.copyProperties(salesLogModel, salesLogDO);

            int result = salesLogDOMapper.insertSelective(salesLogDO);

            if (result == 0) throw new ArithmeticException("销量流水插入失败");

            return true;
        }
        SalesLogDO salesLogDO = salesLogDOs.get(0);

        //更新draft的流水状态为cancel，扣回销量
        if (salesLogDO.getStatus().equals(SalesLogModel.STATUS_DRAFT)) {
            salesLogDOMapper.updateStatus(salesLogDO.getId(), SalesLogModel.STATUS_DRAFT, SalesLogModel.STATUS_CANCEL);
            movieDOMapper.reduceSales(movieId, sale);
        }
        Date end = new Date();
        logger.info("DefaultMovieServiceAPIImpl rollback spend time " + (end.getTime() - start.getTime()));
        return true;
    }

    private MovieModel convertFromMovieDO(MovieDO movieDO) {
        MovieModel movieModel = new MovieModel();
        BeanUtils.copyProperties(movieDO, movieModel);
        return movieModel;
    }
}
