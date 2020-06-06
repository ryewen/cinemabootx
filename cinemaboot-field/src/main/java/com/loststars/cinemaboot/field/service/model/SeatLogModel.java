package com.loststars.cinemaboot.field.service.model;

public class SeatLogModel {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private Integer id;

    private String orderId;

    private Integer fieldId;

    private String seatName;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
