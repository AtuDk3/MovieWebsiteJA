package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.RateDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Rate;


import java.util.List;
import java.util.Map;

public interface RateService {

    Rate createRate(RateDTO rateDTO) throws DataNotFoundException;

    Rate getRate(int id);

    Map<String, String> getNumberRatesAndStarOfMovie(int movieId) throws DataNotFoundException;

    Rate updateRate(Rate rate, RateDTO rateDTO) throws DataNotFoundException;

    void deleteRateMonth();
}