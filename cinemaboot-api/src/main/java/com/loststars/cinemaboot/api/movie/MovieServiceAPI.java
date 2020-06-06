package com.loststars.cinemaboot.api.movie;

import com.loststars.cinemaboot.api.movie.model.MovieModel;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;


import java.util.List;

public interface MovieServiceAPI {

    List<MovieModel> listMovieModels();

    MovieModel getMovieModelById(Integer movieId);

    @TwoPhaseBusinessAction(name = "DubboMovieRecord", commitMethod = "commit", rollbackMethod = "rollback")
    boolean record(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderId") String orderId,
                @BusinessActionContextParameter(paramName = "movieId") Integer movieId, @BusinessActionContextParameter(paramName = "sale") Integer sale);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
