package com.loststars.cinemaboot.gateway.controller.vo;

public class ResponseVO {

    private Integer status;

    private Object data;

    public static ResponseVO success(Object data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(data);
        responseVO.setStatus(0);
        return responseVO;
    }

    public static ResponseVO fail(Object data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(99);
        responseVO.setData(data);
        return responseVO;
    }

    public static ResponseVO fail(Object data, Integer status) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(status);
        responseVO.setData(data);
        return responseVO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
