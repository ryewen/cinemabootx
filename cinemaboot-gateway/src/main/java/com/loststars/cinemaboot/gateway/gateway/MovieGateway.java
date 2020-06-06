package com.loststars.cinemaboot.gateway.gateway;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loststars.cinemaboot.api.field.model.FieldModel;
import com.loststars.cinemaboot.api.movie.MovieServiceAPI;
import com.loststars.cinemaboot.api.movie.model.MovieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MovieGateway {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private MovieServiceAPI movieServiceAPI;

    public List<MovieModel> listMovieModels() {
        String key = "MovieModelList";
        List<MovieModel> movieModels = (List<MovieModel>) redisTemplate.opsForValue().get(key);
        if (movieModels != null) return movieModels;
        movieModels = movieServiceAPI.listMovieModels();
        if (movieModels != null) {
            redisTemplate.opsForValue().set(key, movieModels);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return movieModels;
    }

    public MovieModel getMovieModelById(Integer movieId) {
        String key = "MovieModel-" + movieId;
        MovieModel movieModel = (MovieModel) redisTemplate.opsForValue().get(key);
        if (movieModel != null) return movieModel;
        movieModel = movieServiceAPI.getMovieModelById(movieId);
        if (movieModel != null) {
            redisTemplate.opsForValue().set(key, movieModel);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return movieModel;
    }

    public Integer getMovieSalesById(Integer movieId) {
        String key = "MovieSales-" + movieId;
        Integer movieSales = (Integer) redisTemplate.opsForValue().get(key);
        if (movieSales != null) return movieSales;
        movieSales = movieServiceAPI.getMovieModelById(movieId).getSales();
        if (movieSales != null) {
            redisTemplate.opsForValue().set(key, movieSales);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return movieSales;
    }
}
