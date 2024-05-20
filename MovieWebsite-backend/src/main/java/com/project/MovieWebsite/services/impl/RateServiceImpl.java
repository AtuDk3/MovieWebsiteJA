package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.RateDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Rate;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.RateRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RateServiceImpl implements RateService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RateRepository rateRepository;


    @Override
    public Rate createRate(RateDTO rateDTO) throws DataNotFoundException {
        Movie existingMovie= movieRepository.findById(rateDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+rateDTO.getMovieId()));

        User existingUser= userRepository.findById(rateDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+rateDTO.getUserId()));

        Rate newRate= Rate.builder()
                .movie(existingMovie)
                .user(existingUser)
                .description(rateDTO.getDescription())
                .numberStars(rateDTO.getNumStars())
                .build();

        return rateRepository.save(newRate);
    }

    @Override
    public Rate getRate(int id) {
        return rateRepository.findById(id).orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    @Override
    public Rate updateRate(int id, RateDTO rateDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteRate(int id) {
        Optional<Rate> optionalRate= rateRepository.findById(id);
        if(optionalRate.isPresent()){
            rateRepository.deleteById(id);
        }
    }
}
