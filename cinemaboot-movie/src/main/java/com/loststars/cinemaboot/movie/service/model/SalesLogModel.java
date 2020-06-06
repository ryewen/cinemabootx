package com.loststars.cinemaboot.movie.service.model;

public class SalesLogModel {

    public static final String STATUS_DRAFT = "draft";

    public static final String STATUS_CONFIRMED = "confirmed";

    public static final String STATUS_CANCEL = "cancel";

    private Integer id;

    private String orderId;

    private Integer movieId;

    private Integer sale;

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

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
