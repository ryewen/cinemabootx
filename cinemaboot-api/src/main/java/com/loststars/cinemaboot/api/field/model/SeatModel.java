package com.loststars.cinemaboot.api.field.model;

import java.io.Serializable;

public class SeatModel implements Serializable, Comparable<SeatModel> {

    public static final int STATUS_SAVE = 0;

    public static final int STATUS_SALED = 1;

    private Integer id;

    private Integer fieldId;

    private String name;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int compareTo(SeatModel o) {
        return Integer.valueOf(this.getName()).compareTo(Integer.valueOf(o.getName()));
    }
}
