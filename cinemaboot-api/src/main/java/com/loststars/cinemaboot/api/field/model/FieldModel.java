package com.loststars.cinemaboot.api.field.model;

import com.loststars.cinemaboot.api.movie.model.MovieModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FieldModel implements Serializable {

    private Integer id;

    private String name;

    private MovieModel movieModel;

    private Date startTime;

    private Date endTime;

    private List<SeatModel> seatModels;

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

    public MovieModel getMovieModel() {
        return movieModel;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<SeatModel> getSeatModels() {
        return seatModels;
    }

    public void setSeatModels(List<SeatModel> seatModels) {
        this.seatModels = seatModels;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
