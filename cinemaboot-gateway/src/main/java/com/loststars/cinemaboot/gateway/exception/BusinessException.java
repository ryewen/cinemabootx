package com.loststars.cinemaboot.gateway.exception;

public class BusinessException extends RuntimeException implements CommonException {

    private Integer errCode;

    private String errMsg;

    public BusinessException(EmBusinessException emBusinessException) {
        super();
        this.errCode = emBusinessException.getErrCode();
        this.errMsg = emBusinessException.getErrMsg();
    }

    public BusinessException(EmBusinessException emBusinessException, String errMsg) {
        super();
        this.errCode = emBusinessException.getErrCode();
        this.errMsg = errMsg;
    }

    @Override
    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    @Override
    public Integer getErrCode() {
        return errCode;
    }

    @Override
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }
}
