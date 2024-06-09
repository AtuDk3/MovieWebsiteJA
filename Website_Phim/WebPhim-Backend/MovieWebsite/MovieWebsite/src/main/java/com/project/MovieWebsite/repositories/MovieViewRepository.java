package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieViewRepository extends JpaRepository<MovieView, Integer> {

    List<MovieView> findByViewDate(LocalDate viewDate);

    Optional<MovieView> findByMovieAndViewDate(int movieId, LocalDate viewDate);

    @Query("SELECT mv.movie, SUM(mv.views) as totalViews " +
            "FROM MovieView mv " +
            "WHERE mv.viewDate BETWEEN :startDate AND :endDate " +
            "GROUP BY mv.movie " +
            "ORDER BY totalViews DESC")
    List<MovieView> findTopMoviesByViewsBetweenDates(LocalDate startDate, LocalDate endDate);
}
