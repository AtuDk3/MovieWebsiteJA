package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.repositories.MovieTypeRepository;
import com.project.MovieWebsite.services.MovieTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MovieTypeServiceImpl implements MovieTypeService {

    private final MovieTypeRepository movieTypeRepository;
    @Override
    public MovieType createMovieType(MovieTypeDTO movieTypeDTO) {
        MovieType newMovieType = MovieType.builder().name(movieTypeDTO.getName()).isActive(movieTypeDTO.getIsActive()).build();
        return movieTypeRepository.save(newMovieType);
    }

    @Override
    public MovieType getMovieTypeById(int id) {
        return movieTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie Type not found"));
    }

    @Override
    public List<MovieType> getAllMovieType() {
        return movieTypeRepository.findAll();
    }

    @Override
    public MovieType updateMovieType(int id, MovieTypeDTO movieTypeDTO) {
        MovieType existingMovieType= getMovieTypeById(id);
        existingMovieType.setName(movieTypeDTO.getName());
        existingMovieType.setIsActive(movieTypeDTO.getIsActive());
        movieTypeRepository.save(existingMovieType);
        return existingMovieType;
    }

    @Override
    public void deleteMovieType(int id) {
        movieTypeRepository.deleteById(id);
    }
}
