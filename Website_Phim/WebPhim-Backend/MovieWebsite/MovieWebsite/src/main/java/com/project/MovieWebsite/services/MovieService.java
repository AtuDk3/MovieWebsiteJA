package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.responses.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MovieService {

    Movie createMovie (MovieDTO movieDTO) throws DataNotFoundException;

    Movie getMovieById(int id);

    Page<MovieResponse> getAllMovies(String keyword, int genreId, PageRequest pageRequest);

    Movie updateMovies(int id, MovieDTO movieDTO) throws Exception;

    void deleteMovies(int id);

    boolean existByName(String name);
}