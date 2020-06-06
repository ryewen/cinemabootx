package com.loststars.cinemaboot.field.dao;

import com.loststars.cinemaboot.field.dataobject.SeatDO;
import com.loststars.cinemaboot.field.dataobject.SeatDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeatDOMapper {
    long countByExample(SeatDOExample example);

    int deleteByExample(SeatDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SeatDO record);

    int insertSelective(SeatDO record);

    List<SeatDO> selectByExample(SeatDOExample example);

    SeatDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SeatDO record, @Param("example") SeatDOExample example);

    int updateByExample(@Param("record") SeatDO record, @Param("example") SeatDOExample example);

    int updateByPrimaryKeySelective(SeatDO record);

    int updateByPrimaryKey(SeatDO record);

    int sellSeat(SeatDO record);

    int saveSeat(SeatDO record);
}