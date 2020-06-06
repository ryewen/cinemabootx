package com.loststars.cinemaboot.gateway.controller;

import com.loststars.cinemaboot.gateway.controller.vo.ResponseVO;
import com.loststars.cinemaboot.gateway.exception.BusinessException;
import com.loststars.cinemaboot.gateway.exception.EmBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVO handleException(HttpServletRequest request, Exception e) {
        BusinessException businessException = null;
        if (e instanceof BusinessException) {
            businessException = (BusinessException) e;
        } else {
            e.printStackTrace();
            businessException = new BusinessException(EmBusinessException.UNKNOW_EXCEPTION);
        }
        return ResponseVO.fail(businessException, businessException.getErrCode());
    }
}
