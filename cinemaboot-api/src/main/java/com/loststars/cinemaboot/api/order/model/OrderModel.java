package com.loststars.cinemaboot.api.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderModel implements Serializable {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_PAYING = "paying";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private String id;

    private Integer userId;

    private Integer movieId;

    private Integer fieldId;

    private String seatName;

    private BigDecimal cost;

    private Date createTime;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
