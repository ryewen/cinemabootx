package com.loststars.cinemaboot.gateway.gateway;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.field.FieldServiceAPI;
import com.loststars.cinemaboot.api.field.model.FieldModel;
import com.loststars.cinemaboot.api.field.model.SeatModel;
import com.loststars.cinemaboot.gateway.Service.CacheService;
import com.loststars.cinemaboot.gateway.controller.OrderController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class FieldGateway {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private FieldServiceAPI fieldServiceAPI;

    @Autowired
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(FieldGateway.class);

    @HystrixCommand(fallbackMethod = "getFieldModelByFieldIdFallback",
                    commandProperties = {
                        @HystrixProperty(name="execution.isolation.strategy", value = "THREAD"), //隔离策略 线程池
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "400"), //超时时间
                        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4000"), //熔断器窗口中请求数大于4000，则判断一次
                        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") //熔断器熔断阈值
                    },
                    threadPoolProperties = {
                        @HystrixProperty(name = "coreSize", value = "150"),
                        @HystrixProperty(name = "maxQueueSize", value = "1000"),
                        @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1000"), //即便没达到最大队列长度，若队列长度大于此值，也拒绝
                        @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"), //统计窗口分为10个桶
                        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1000") //统计窗口时间
                    }
    )
    public FieldModel getFieldModelByFieldId(Integer fieldId) {
        logger.info("FieldGateway getFieldModelByFieldId " + fieldId);
        String key = "FieldModel-" + fieldId;
        FieldModel fieldModel = (FieldModel) redisTemplate.opsForValue().get(key);
        if (fieldModel == null) {
            fieldModel = fieldServiceAPI.getFieldModelById(fieldId);
            if (fieldModel != null) {
                redisTemplate.opsForValue().set(key, fieldModel);
                redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            }
        }
        if (fieldModel != null) {
            cacheService.setCache(key, fieldModel);
        }
        return fieldModel;
    }

    public FieldModel getFieldModelByFieldIdFallback(Integer fieldId) {
        logger.info("FieldGateway getFieldModelByFieldIdFallback " + fieldId);
        String key = "FieldModel-" + fieldId;
        return (FieldModel) cacheService.getCache(key);
    }

    public Set<SeatModel> getSaveSeatModelSetByFieldId(Integer fieldId) {
        String emptyKey = "FieldEmpty-" + fieldId;
        if (redisTemplate.hasKey(emptyKey)) return new HashSet<SeatModel>();
        String key = "SaveSeatModelSet-FieldId-" + fieldId;
        Set<Object> saveSeatModelSet = redisTemplate.opsForZSet().range(key, 0, -1);
        if (saveSeatModelSet.size() != 0) {
            Set<SeatModel> set = saveSeatModelSet.stream().map((saveSeatModel) -> {
                return (SeatModel) saveSeatModel;
            }).collect(Collectors.toCollection(TreeSet::new));
            return set;
        }
        FieldModel fieldModel = fieldServiceAPI.getFieldModelById(fieldId);
        if (fieldModel == null) return null;
        List<SeatModel> seatModels = fieldModel.getSeatModels();
        if (seatModels == null) return null;
        if (seatModels.isEmpty()) {
            redisTemplate.opsForValue().set(emptyKey, "empty");
            redisTemplate.expire(emptyKey, 30, TimeUnit.MINUTES);
            return new HashSet<SeatModel>();
        }
        Set<SeatModel> set = new TreeSet<>();
        seatModels.forEach((seatModel) -> {
            if (seatModel.getStatus() == SeatModel.STATUS_SAVE) {
                redisTemplate.opsForZSet().add(key, seatModel, Double.valueOf(seatModel.getName()));
                set.add(seatModel);
            }
        });
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        return set;
    }

    public List<FieldModel> listFieldModelsByMovieId(Integer movieId) {
        String key = "FieldModels-MovieId-" + movieId;
        List<FieldModel> fieldModels = (List<FieldModel>) redisTemplate.opsForValue().get(key);
        if (fieldModels != null) return fieldModels;
        fieldModels = fieldServiceAPI.listFieldModelsByMovieId(movieId);
        if (fieldModels != null) {
            redisTemplate.opsForValue().set(key, fieldModels);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return fieldModels;
    }
}
