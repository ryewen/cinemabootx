package com.loststars.cinemaboot.api.movie.model;

import com.loststars.cinemaboot.api.field.model.FieldModel;

import java.io.Serializable;
import java.util.List;

public class MovieModel implements Serializable {

    private Integer id;

    private String name;

    private String director;

    private String actors;

    private String detail;

    private String imgUrl;

    private Integer sales;

    private List<FieldModel> fieldModels;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public List<FieldModel> getFieldModels() {
        return fieldModels;
    }

    public void setFieldModels(List<FieldModel> fieldModels) {
        this.fieldModels = fieldModels;
    }
}
