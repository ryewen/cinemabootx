package com.loststars.cinemaboot.user.dao;

import com.loststars.cinemaboot.user.dataobject.UserDO;
import com.loststars.cinemaboot.user.dataobject.UserDOExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDOMapper {
    long countByExample(UserDOExample example);

    int deleteByExample(UserDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByExample(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    int reduceWallet(@Param("userId") Integer userId, @Param("cost") BigDecimal cost);

    int addWallet(@Param("userId") Integer userId, @Param("cost") BigDecimal cost);
}