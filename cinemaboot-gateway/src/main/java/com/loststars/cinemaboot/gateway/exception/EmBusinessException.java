package com.loststars.cinemaboot.gateway.exception;

public enum EmBusinessException implements CommonException {

    PARAMETER_VALIDATION_EXCEPTION(10001, "参数不合法"),
    UNKNOW_EXCEPTION(10002, "未知异常"),

    USER_NOEXIST(20001, "用户不存在"),
    USER_NOLOGIN(20002, "用户未登录"),

    MOVIE_BEGIN(30001, "电影已开始"),
    MOVIE_END(30002, "电影已结束")
    ;

    private Integer errCode;

    private String errMsg;

    private EmBusinessException(Integer errCode, String errMsg) {
        this.errCode = errCode;
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
