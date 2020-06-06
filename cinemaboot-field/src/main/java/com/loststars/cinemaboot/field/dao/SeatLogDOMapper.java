package com.loststars.cinemaboot.field.dao;

import com.loststars.cinemaboot.field.dataobject.SeatLogDO;
import com.loststars.cinemaboot.field.dataobject.SeatLogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeatLogDOMapper {
    long countByExample(SeatLogDOExample example);

    int deleteByExample(SeatLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SeatLogDO record);

    int insertSelective(SeatLogDO record);

    List<SeatLogDO> selectByExample(SeatLogDOExample example);

    SeatLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SeatLogDO record, @Param("example") SeatLogDOExample example);

    int updateByExample(@Param("record") SeatLogDO record, @Param("example") SeatLogDOExample example);

    int updateByPrimaryKeySelective(SeatLogDO record);

    int updateByPrimaryKey(SeatLogDO record);

    int updateStatus(@Param("id") Integer id, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}