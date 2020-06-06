package com.loststars.cinemaboot.gateway.controller.vo;

import java.util.List;

public class MovieVO {

    private Integer id;

    private String name;

    private String imgUrl;

    private String director;

    private String actors;

    private String detail;

    private Integer sales;

    private List<FieldVO> fieldVOs;

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

    public List<FieldVO> getFieldVOs() {
        return fieldVOs;
    }

    public void setFieldVOs(List<FieldVO> fieldVOs) {
        this.fieldVOs = fieldVOs;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}
