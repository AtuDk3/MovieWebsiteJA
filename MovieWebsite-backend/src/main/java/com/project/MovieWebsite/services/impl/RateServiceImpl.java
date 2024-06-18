package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.RateDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.*;
import com.project.MovieWebsite.repositories.ManagerStorageRateRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.RateRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RateServiceImpl implements RateService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RateRepository rateRepository;
    private final ManagerStorageRateRepository managerStorageRateRepository;

    @Override
    public Rate createRate(RateDTO rateDTO) throws DataNotFoundException {

        Movie existingMovie= movieRepository.findById(rateDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+rateDTO.getMovieId()));

        User existingUser= userRepository.findById(rateDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+rateDTO.getUserId()));

        Rate findRateByMovieAndUser= rateRepository.findByMovieAndUser(existingMovie, existingUser);
        if (findRateByMovieAndUser==null){
            Rate newRate= Rate.builder()
                    .movie(existingMovie)
                    .user(existingUser)
                    .numberStars(rateDTO.getNumStars())
                    .rateDate(LocalDate.now())
                    .build();
            return rateRepository.save(newRate);
        }else{
            findRateByMovieAndUser.setNumberStars(rateDTO.getNumStars());
            findRateByMovieAndUser.setRateDate(LocalDate.now());
            return rateRepository.save(findRateByMovieAndUser);
        }


    }

    @Override
    public Movie getRateByMovie(int movieId) throws DataNotFoundException{
        Movie existingMovie= movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+movieId));

        return existingMovie;
    }

    @Override
    public Map<String, String> getNumberRatesAndStarOfMovie(int movieId) throws DataNotFoundException{
        Movie existingMovie= movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+movieId));
        List<Rate> listRate= rateRepository.findAllByMovie(existingMovie);
        Map<String, String> resultMap = new HashMap<>();
        existingMovie.setNumberRate(listRate.size());
        resultMap.put("rate", existingMovie.getNumberRate()+"");
        float numberStars= 0;
        for(Rate rate: listRate){
            numberStars+=rate.getNumberStars();
        }
        existingMovie.setAverageStar(numberStars/listRate.size());
        resultMap.put("average", existingMovie.getAverageStar()+"");
        movieRepository.save(existingMovie);
        return resultMap;
    }

    @Override
    public Rate updateRate(Rate rate, RateDTO rateDTO) throws DataNotFoundException {
        rate.setNumberStars(rate.getNumberStars());
        return rateRepository.save(rate);
    }

    @Override
    public void deleteRateMonth() {
        ManagerStorageRate managerStorageRate= managerStorageRateRepository.getAll();
        if(managerStorageRate!=null){
            managerStorageRate.setLastDelete(LocalDate.now());
            managerStorageRateRepository.save(managerStorageRate);
            LocalDate cutoffDate = LocalDate.now().minusMonths(1);
            rateRepository.deleteRateOlderThanMonth(cutoffDate);
        }

    }
}