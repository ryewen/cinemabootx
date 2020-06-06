package com.loststars.cinemaboot.field.dao;

import com.loststars.cinemaboot.field.dataobject.FieldDO;
import com.loststars.cinemaboot.field.dataobject.FieldDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FieldDOMapper {
    long countByExample(FieldDOExample example);

    int deleteByExample(FieldDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FieldDO record);

    int insertSelective(FieldDO record);

    List<FieldDO> selectByExample(FieldDOExample example);

    FieldDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FieldDO record, @Param("example") FieldDOExample example);

    int updateByExample(@Param("record") FieldDO record, @Param("example") FieldDOExample example);

    int updateByPrimaryKeySelective(FieldDO record);

    int updateByPrimaryKey(FieldDO record);
}