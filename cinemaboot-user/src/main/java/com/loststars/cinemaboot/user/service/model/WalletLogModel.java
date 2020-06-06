package com.loststars.cinemaboot.user.service.model;

import java.math.BigDecimal;

public class WalletLogModel {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private Integer id;

    private String orderId;

    private Integer userId;

    private BigDecimal wallet;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
