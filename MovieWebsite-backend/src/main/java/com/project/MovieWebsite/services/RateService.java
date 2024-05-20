package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.RateDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Rate;


import java.util.List;

public interface RateService {

    Rate createRate(RateDTO rateDTO) throws DataNotFoundException;

    Rate getRate(int id);

    List<Rate> getAllRates();

    Rate updateRate(int id, RateDTO rateDTO) throws DataNotFoundException;

    void deleteRate(int id);
}
