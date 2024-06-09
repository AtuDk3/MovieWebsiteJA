package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.MovieViewRepository;
import com.project.MovieWebsite.services.MovieViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieViewServiceImpl implements MovieViewService {

    private final MovieViewRepository movieViewRepository;
    private final MovieRepository movieRepository;


    public List<Movie> getTopMoviesByDay(LocalDate date) {
        return movieViewRepository.findByViewDate(date).stream()
                .sorted((a, b) -> Integer.compare(b.getViews(), a.getViews()))
                .map(MovieView::getMovie)
                .collect(Collectors.toList());
    }

    public List<MovieView> getTopMoviesByWeek(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return movieViewRepository.findTopMoviesByViewsBetweenDates(startOfWeek, endOfWeek);
    }

    public List<MovieView> getTopMoviesByMonth(LocalDate startOfMonth) {
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        return movieViewRepository.findTopMoviesByViewsBetweenDates(startOfMonth, endOfMonth);
    }

    public void incrementMovieView(int movieId) throws DataNotFoundException{
        LocalDate today = LocalDate.now();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        MovieView movieView = movieViewRepository.findByMovieAndViewDate(movieId, today)
                .orElseGet(() -> new MovieView(movie, today, 0));
        movieView.setViews(movieView.getViews() + 1);
        movieViewRepository.save(movieView);

        movie.setNumberView(movie.getNumberView() + 1);
        movieRepository.save(movie);
    }
}
