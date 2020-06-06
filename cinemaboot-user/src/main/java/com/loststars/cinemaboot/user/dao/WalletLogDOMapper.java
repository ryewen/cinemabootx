package com.loststars.cinemaboot.user.dao;

import com.loststars.cinemaboot.user.dataobject.WalletLogDO;
import com.loststars.cinemaboot.user.dataobject.WalletLogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletLogDOMapper {
    long countByExample(WalletLogDOExample example);

    int deleteByExample(WalletLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletLogDO record);

    int insertSelective(WalletLogDO record);

    List<WalletLogDO> selectByExample(WalletLogDOExample example);

    WalletLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WalletLogDO record, @Param("example") WalletLogDOExample example);

    int updateByExample(@Param("record") WalletLogDO record, @Param("example") WalletLogDOExample example);

    int updateByPrimaryKeySelective(WalletLogDO record);

    int updateByPrimaryKey(WalletLogDO record);

    int updateStatus(@Param("id") Integer id, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}