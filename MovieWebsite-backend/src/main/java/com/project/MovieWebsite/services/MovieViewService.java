package com.project.MovieWebsite.services;

import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieView;

import java.time.LocalDate;
import java.util.List;

public interface MovieViewService {

    List<Movie> getTopMoviesByDay();

    List<Movie> getTopMoviesByWeek();

    List<Movie> getTopMoviesByMonth();

    void incrementMovieView(int movieId) throws DataNotFoundException;

    void updateViewsForDay();

    void deleteOldViewsMonth();
}