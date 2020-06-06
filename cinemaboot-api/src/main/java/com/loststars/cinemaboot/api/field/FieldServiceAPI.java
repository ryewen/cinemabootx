package com.loststars.cinemaboot.api.field;

import com.loststars.cinemaboot.api.field.model.FieldModel;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

public interface FieldServiceAPI {

    List<FieldModel> listFieldModelsByMovieId(Integer movieId);

    FieldModel getFieldModelById(Integer fieldId);

    @TwoPhaseBusinessAction(name = "DubboFieldRecord", commitMethod = "commit", rollbackMethod = "rollback")
    boolean record(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderId") String orderId,
                @BusinessActionContextParameter(paramName = "fieldId") Integer fieldId, @BusinessActionContextParameter(paramName = "seatName") String seatName,
                @BusinessActionContextParameter(paramName = "seatId") Integer seatId);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
