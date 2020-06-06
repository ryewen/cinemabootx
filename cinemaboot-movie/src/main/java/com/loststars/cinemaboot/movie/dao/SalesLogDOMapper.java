package com.loststars.cinemaboot.movie.dao;

import com.loststars.cinemaboot.movie.dataobject.SalesLogDO;
import com.loststars.cinemaboot.movie.dataobject.SalesLogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesLogDOMapper {
    long countByExample(SalesLogDOExample example);

    int deleteByExample(SalesLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesLogDO record);

    int insertSelective(SalesLogDO record);

    List<SalesLogDO> selectByExample(SalesLogDOExample example);

    SalesLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesLogDO record, @Param("example") SalesLogDOExample example);

    int updateByExample(@Param("record") SalesLogDO record, @Param("example") SalesLogDOExample example);

    int updateByPrimaryKeySelective(SalesLogDO record);

    int updateByPrimaryKey(SalesLogDO record);

    int updateStatus(@Param("id") Integer id, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}