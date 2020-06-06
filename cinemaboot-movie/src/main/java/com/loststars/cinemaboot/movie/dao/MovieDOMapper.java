package com.loststars.cinemaboot.movie.dao;

import com.loststars.cinemaboot.movie.dataobject.MovieDO;
import com.loststars.cinemaboot.movie.dataobject.MovieDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MovieDOMapper {
    long countByExample(MovieDOExample example);

    int deleteByExample(MovieDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MovieDO record);

    int insertSelective(MovieDO record);

    List<MovieDO> selectByExample(MovieDOExample example);

    MovieDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MovieDO record, @Param("example") MovieDOExample example);

    int updateByExample(@Param("record") MovieDO record, @Param("example") MovieDOExample example);

    int updateByPrimaryKeySelective(MovieDO record);

    int updateByPrimaryKey(MovieDO record);

    int addSales(@Param("id") Integer id, @Param("sale") Integer sale);

    int reduceSales(@Param("id") Integer id, @Param("sale") Integer sale);
}