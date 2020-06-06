package com.loststars.cinemaboot.api.user;

import com.loststars.cinemaboot.api.user.model.UserModel;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;


import java.math.BigDecimal;

public interface UserServiceAPI {

    UserModel getUserModelByUsername(String username);

    UserModel getUserModelByTelephone(String telephone);

    boolean createUser(UserModel userModel);

    BigDecimal getWalletByUserId(Integer userId);

    @TwoPhaseBusinessAction(name = "DubboUserRecord", commitMethod = "commit", rollbackMethod = "rollback")
    boolean record(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderId") String orderId,
                @BusinessActionContextParameter(paramName = "userId") Integer userId, @BusinessActionContextParameter(paramName = "cost") BigDecimal cost);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
