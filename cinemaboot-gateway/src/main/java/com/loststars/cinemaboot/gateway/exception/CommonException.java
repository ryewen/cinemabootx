package com.loststars.cinemaboot.gateway.exception;

public interface CommonException {

    void setErrCode(Integer errCode);

    Integer getErrCode();

    void setErrMsg(String errMsg);

    String getErrMsg();
}
