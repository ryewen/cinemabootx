package com.loststars.cinemaboot.api.order;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;
import java.util.Date;

public interface OrderServiceAPI {

    boolean createOrder(String orderId, Integer userId, Integer movieId, Integer fieldId, String seatName, BigDecimal cost, Date createTime);

    @TwoPhaseBusinessAction(name = "DubboOrderRecord", commitMethod = "commit", rollbackMethod = "rollback")
    boolean record(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderId") String orderId);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
