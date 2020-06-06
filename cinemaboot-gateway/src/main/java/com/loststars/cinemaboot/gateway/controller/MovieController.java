package com.loststars.cinemaboot.gateway.controller;

import com.loststars.cinemaboot.api.field.FieldServiceAPI;
import com.loststars.cinemaboot.api.field.model.FieldModel;
import com.loststars.cinemaboot.api.field.model.SeatModel;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.movie.model.MovieModel;
import com.loststars.cinemaboot.api.user.model.UserModel;
import com.loststars.cinemaboot.gateway.controller.vo.FieldVO;
import com.loststars.cinemaboot.gateway.controller.vo.MovieFieldVO;
import com.loststars.cinemaboot.gateway.controller.vo.MovieVO;
import com.loststars.cinemaboot.gateway.controller.vo.ResponseVO;
import com.loststars.cinemaboot.gateway.exception.BusinessException;
import com.loststars.cinemaboot.gateway.exception.EmBusinessException;
import com.loststars.cinemaboot.gateway.gateway.FieldGateway;
import com.loststars.cinemaboot.gateway.gateway.MovieGateway;
import com.loststars.cinemaboot.gateway.gateway.UserGateway;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class MovieController extends BaseController {

    @Autowired
    private FieldGateway fieldGateway;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private MovieGateway movieGateway;

    @GetMapping("/listAll")
    public ResponseVO listMovies() {
        List<MovieModel> movieModels = movieGateway.listMovieModels();

        movieModels.forEach((movieModel) -> {
            Integer movieSales = movieGateway.getMovieSalesById(movieModel.getId());
            movieModel.setSales(movieSales);
        });

        return ResponseVO.success(movieModels);
    }

    @GetMapping("/getMovie")
    public ResponseVO getMovieById(@RequestParam("movieId") Integer movieId) {
        //参数校验
        if (movieId == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION);

        //根据id查询影片信息，包括影片的场次
        MovieModel movieModel = movieGateway.getMovieModelById(movieId);
        if (movieModel == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "影片不存在");

        //查询销量
        Integer movieSales = movieGateway.getMovieSalesById(movieId);
        movieModel.setSales(movieSales);

        List<FieldModel> fieldModels = fieldGateway.listFieldModelsByMovieId(movieId);
        if (fieldModels == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "影片场次信息不存在");

        List<FieldVO> fieldVOs = fieldModels.stream().map((fieldModel) -> {
            FieldVO fieldVO = new FieldVO();
            BeanUtils.copyProperties(fieldModel, fieldVO);

            fieldVO.setStartTime(new DateTime(fieldModel.getStartTime()).toString("yyyy-MM-dd HH:mm"));
            fieldVO.setEndTime(new DateTime(fieldModel.getEndTime()).toString("yyyy-MM-dd HH:mm"));

            int saveSeats = 0, saledSeats = 0;
            Set<SeatModel> saveSeatModelSet = fieldGateway.getSaveSeatModelSetByFieldId(fieldModel.getId());
            if (saveSeatModelSet == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "影片场次座位信息不存在");

            saveSeats = saveSeatModelSet.size();
            saledSeats = fieldModel.getSeatModels().size() - saveSeats;

            fieldVO.setSaveSeats(saveSeats);
            fieldVO.setSaledSeats(saledSeats);
            return fieldVO;
        }).collect(Collectors.toList());

        MovieVO movieVO = new MovieVO();
        movieVO.setFieldVOs(fieldVOs);
        movieVO.setName(movieModel.getName());
        movieVO.setId(movieId);
        movieVO.setImgUrl(movieModel.getImgUrl());
        movieVO.setSales(movieModel.getSales());
        movieVO.setDirector(movieModel.getDirector());
        movieVO.setDetail(movieModel.getDetail());
        movieVO.setActors(movieModel.getActors());

        return ResponseVO.success(movieVO);
    }

    @GetMapping("/getField")
    public ResponseVO getFieldById(@RequestParam("fieldId") Integer fieldId, @RequestParam("token") String token) {
        if (fieldId == null || StringUtils.isEmpty(token)) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION);

        //查询场次信息、电影信息、座位信息
        FieldModel fieldModel = fieldGateway.getFieldModelByFieldId(fieldId);
        if (fieldModel == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "场次不存在");

        MovieFieldVO movieFieldVO = new MovieFieldVO();

        Integer movieSales = movieGateway.getMovieSalesById(fieldModel.getMovieModel().getId());
        movieFieldVO.setSales(movieSales);

        movieFieldVO.setStartTime(new DateTime(fieldModel.getStartTime()).toString("yyyy-MM-dd HH:mm"));

        Set<SeatModel> saveSeatModelSet = fieldGateway.getSaveSeatModelSetByFieldId(fieldId);
        if (saveSeatModelSet == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "影片场次座位信息不存在");

        StringBuilder builder = new StringBuilder();
        saveSeatModelSet.forEach((seatModel) -> {
            builder.append(seatModel.getName());
            builder.append(",");
        });
        if (builder.length() != 0) {
            builder.deleteCharAt(builder.length() - 1);
        } else {
            builder.append("座位已售罄");
        }
        movieFieldVO.setSeats(builder.toString());

        movieFieldVO.setPrice(fieldModel.getPrice());
        movieFieldVO.setMovieName(fieldModel.getMovieModel().getName());
        movieFieldVO.setMovieId(fieldModel.getMovieModel().getId());
        movieFieldVO.setFieldName(fieldModel.getName());
        movieFieldVO.setFieldId(fieldModel.getId());
        movieFieldVO.setEndTime(new DateTime(fieldModel.getEndTime()).toString("yyyy-MM-dd HH:mm"));
        movieFieldVO.setDirector(fieldModel.getMovieModel().getDirector());
        movieFieldVO.setActors(fieldModel.getMovieModel().getActors());
        movieFieldVO.setImgUrl(fieldModel.getMovieModel().getImgUrl());

        UserModel userModel = userGateway.getUserModelByToken(token);
        BigDecimal wallet = new BigDecimal(0);
        if (userModel != null) wallet = userGateway.getWalletByUserId(userModel.getId());
        movieFieldVO.setWallet(wallet.toString());

        return ResponseVO.success(movieFieldVO);
    }

}
