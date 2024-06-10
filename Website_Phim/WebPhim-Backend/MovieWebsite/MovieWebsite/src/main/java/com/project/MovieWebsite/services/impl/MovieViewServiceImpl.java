package com.project.MovieWebsite.services.impl;


import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieViewExecuted;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.MovieViewExecutedRepository;
import com.project.MovieWebsite.repositories.MovieViewRepository;
import com.project.MovieWebsite.services.MovieViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieViewServiceImpl implements MovieViewService {

    private final MovieViewRepository movieViewRepository;
    private final MovieRepository movieRepository;
    private final MovieViewExecutedRepository movieViewExecutedRepository;

    public List<Movie> getTopMoviesByViews(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = movieViewRepository.findTopMoviesByViewsBetweenDates(startDate, endDate);
        return results.stream().map(result -> (Movie) result[0]).collect(Collectors.toList());
    }

    public List<Movie> getTopMoviesByDay() {
        LocalDate today = LocalDate.now();
        return getTopMoviesByViews(today, today);
    }

    public List<Movie> getTopMoviesByWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        return getTopMoviesByViews(startOfWeek, endOfWeek);
    }

    public List<Movie> getTopMoviesByMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return getTopMoviesByViews(startOfMonth, endOfMonth);
    }

    public void incrementMovieView(int movieId) throws DataNotFoundException{
        LocalDate today = LocalDate.now();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        MovieView movieView = movieViewRepository.findByMovieAndViewDate(movie, today)
                .orElseGet(() -> new MovieView(movie, today, 0));
        movieView.setViews(movieView.getViews() + 1);
        movieViewRepository.save(movieView);

        movie.setNumberView(movie.getNumberView() + 1);
        movieRepository.save(movie);
    }

    public void updateViewsForDay() {

        MovieViewExecuted movieViewExecuted= movieViewExecutedRepository.findByDateExecuted(LocalDate.now());
        if(movieViewExecuted==null) {
            LocalDate today = LocalDate.now();
            LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
            if (!LocalDate.now().equals(monday)) {
                LocalDate yesterday = today.minusDays(1);
                List<Object[]> results = movieViewRepository.findTopMoviesByViewsBetweenDates(monday, yesterday);

                for (Object[] result : results) {
                    Movie movie = (Movie) result[0];
                    Integer totalViews = (Integer) result[1];
                    MovieView movieView = movieViewRepository.findByMovieAndViewDate(movie, yesterday).orElse(new MovieView(movie, yesterday, 0));
                    movieView.setViews(totalViews);
                    movieViewRepository.save(movieView);
                }
                movieViewExecuted= MovieViewExecuted.builder()
                        .dateExecuted(today)
                        .isExecuted(1)
                        .build();
                movieViewExecutedRepository.save(movieViewExecuted);
                movieViewRepository.deleteMovieViewsBetweenDates(monday, yesterday.minusDays(1));
            }
        }
    }

    @Override
    public void deleteOldViewsMonth() {
        LocalDate cutoffDate = LocalDate.now().minusMonths(1);
        movieViewRepository.deleteViewsOlderThanMonth(cutoffDate);
    }


}
