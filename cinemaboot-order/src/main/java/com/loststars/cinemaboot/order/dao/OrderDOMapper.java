package com.loststars.cinemaboot.order.dao;

import com.loststars.cinemaboot.order.dataobject.OrderDO;
import com.loststars.cinemaboot.order.dataobject.OrderDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDOMapper {
    long countByExample(OrderDOExample example);

    int deleteByExample(OrderDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    List<OrderDO> selectByExample(OrderDOExample example);

    OrderDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByExample(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    int updateStatus(@Param("orderId") String orderId, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}