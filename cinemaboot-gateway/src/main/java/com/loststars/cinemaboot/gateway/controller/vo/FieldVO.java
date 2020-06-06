package com.loststars.cinemaboot.gateway.controller.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FieldVO {

    private Integer id;

    private String name;

    private String startTime;

    private String endTime;

    private Integer saveSeats;

    private Integer saledSeats;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSaveSeats() {
        return saveSeats;
    }

    public void setSaveSeats(Integer saveSeats) {
        this.saveSeats = saveSeats;
    }

    public Integer getSaledSeats() {
        return saledSeats;
    }

    public void setSaledSeats(Integer saledSeats) {
        this.saledSeats = saledSeats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
