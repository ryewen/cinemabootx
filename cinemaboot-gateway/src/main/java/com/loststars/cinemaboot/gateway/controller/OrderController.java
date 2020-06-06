package com.loststars.cinemaboot.gateway.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.field.model.FieldModel;
import com.loststars.cinemaboot.api.field.model.SeatModel;
import com.loststars.cinemaboot.api.order.OrderServiceAPI;
import com.loststars.cinemaboot.api.user.model.UserModel;
import com.loststars.cinemaboot.gateway.controller.vo.ResponseVO;
import com.loststars.cinemaboot.gateway.exception.BusinessException;
import com.loststars.cinemaboot.gateway.exception.EmBusinessException;
import com.loststars.cinemaboot.gateway.gateway.FieldGateway;
import com.loststars.cinemaboot.gateway.gateway.OrderGateway;
import com.loststars.cinemaboot.gateway.gateway.UserGateway;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private FieldGateway fieldGateway;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private OrderGateway orderGateway;

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/createOrder")
    public ResponseVO createOrder(String token, Integer fieldId, String seatName) {
        if (StringUtils.isEmpty(token) || fieldId == null || StringUtils.isEmpty(seatName)) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION);

        //验证token是否有效，并查询对应用户
        UserModel userModel = userGateway.getUserModelByToken(token);
        if (userModel == null) throw new BusinessException(EmBusinessException.USER_NOLOGIN);

        //验证fieldId是否有效，并查询对应影片
        FieldModel fieldModel = fieldGateway.getFieldModelByFieldId(fieldId);
        if (fieldModel == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "影片场次信息不存在");

        //验证缓存中座位是否存在，并空余
        Set<SeatModel> saveSeatModelSet = fieldGateway.getSaveSeatModelSetByFieldId(fieldId);
        if (saveSeatModelSet == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "座位信息不存在");
        if (saveSeatModelSet.isEmpty()) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "座位已售罄");

        Integer seatId = null;
        for (SeatModel seatModel : saveSeatModelSet) {
            if (seatModel.getName().equals(seatName)) {
                seatId = seatModel.getId();
                break;
            }
        }
        if (seatId == null) throw new BusinessException(EmBusinessException.PARAMETER_VALIDATION_EXCEPTION, "所选座位已售出或不存在");

        //根据userId,movieId,fieldId,seatName,时间,订单状态draft创建订单
        StringBuilder builder = new StringBuilder();

        //校验下单时间
        DateTime dateTime = new DateTime();
        if (dateTime.isAfter(new DateTime(fieldModel.getStartTime()))) {
            if (dateTime.isBefore(new DateTime(fieldModel.getEndTime()))) {
                throw new BusinessException(EmBusinessException.MOVIE_BEGIN);
            } else {
                throw new BusinessException(EmBusinessException.MOVIE_END);
            }

        }

        builder.append(dateTime.toString("yyyyMMddHHmmss"));
        Random random = new Random();
        for (int i = 0; i < 5; ++ i) {
            builder.append(random.nextInt(10));
        }
        String orderId = builder.toString();
        boolean result = orderServiceAPI.createOrder(orderId, userModel.getId(), fieldModel.getMovieModel().getId(), fieldModel.getId(), seatName, fieldModel.getPrice(), dateTime.toDate());

        if (! result) throw new BusinessException(EmBusinessException.UNKNOW_EXCEPTION, "订单创建失败，请稍后重试");

        //TCC
        //Try
        //根据orderId,将订单状态更新为paying
        //扣减座位，记录orderId,座位id流水
        //扣减用户钱包，记录orderId,用户id,资金流水
        //增加影片销量，记录oriderId,影片id,流水

        //confirm
        //将订单状态更新为confirmed
        //将座位流水更新为...
        //将钱包流水更新为...

        //cancel
        //将订单状态更新为cancel
        //回补座位，更新流水状态为cancel
        //回补钱包，更新...

        Date start = new Date();
        try {
            orderGateway.record(orderId, fieldModel.getMovieModel().getId(), 1, userModel.getId(), fieldModel.getPrice(), fieldId, seatName, seatId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessException.UNKNOW_EXCEPTION, e.getMessage());
        }
        Date end = new Date();
        long time = end.getTime() - start.getTime();
        logger.info("OrderController createOrder spend time " + String.valueOf(time));

        //增加缓存影片销量，扣减缓存座位，扣减缓存钱包
        String salesKey = "MovieSales-" + fieldModel.getMovieModel().getId();
        redisTemplate.opsForValue().increment(salesKey, 1);

        String seatsKey = "SaveSeatModelSet-FieldId-" + fieldId;
        redisTemplate.opsForZSet().removeRangeByScore(seatsKey, Double.valueOf(seatName), Double.valueOf(seatName));

        String walletKey = "Wallet-" + userModel.getId();
        double dec =  fieldModel.getPrice().multiply(new BigDecimal(-1)).doubleValue();
        redisTemplate.opsForValue().increment(walletKey, dec);

        return ResponseVO.success(null);
    }
}
