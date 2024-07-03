
package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.responses.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MovieService {

    Page<MovieResponse> getAllSearchMovies(String keyword, PageRequest pageRequest);

    Page<MovieResponse> getAllMovies(String keyword, PageRequest pageRequest);

    Movie createMovie (MovieDTO movieDTO) throws DataNotFoundException;

    Movie getMovieById(int id);

    Page<MovieResponse> getAllMoviesByGenreId(String keyword, int genreId, PageRequest pageRequest);

    Page<MovieResponse> getAllMoviesByCountryId(String keyword, int countryId, PageRequest pageRequest);

    Page<MovieResponse> getAllMoviesByMovieTypeId(String keyword, int movieTypeId, PageRequest pageRequest);

    Page<MovieResponse> getAllMoviesRelated(int movieId, int genreId, String movieName, PageRequest pageRequest);

    Page<MovieResponse> getHotMovies(PageRequest pageRequest);

    Movie updateMovies(int id, MovieDTO movieDTO) throws Exception;

    List<Movie> getMoviesByGenreId(int genreId) throws Exception;

    List<Movie> getMoviesByCountryId(int countryId) throws Exception;

    List<Movie> getMovieByMovieTypeId(int movieTypeId) throws Exception;

    void deleteMovies(int id);

    boolean existByName(String name);
}