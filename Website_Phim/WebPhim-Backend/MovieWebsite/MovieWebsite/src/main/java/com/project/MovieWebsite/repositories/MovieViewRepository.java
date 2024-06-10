package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieViewRepository extends JpaRepository<MovieView, Integer> {

    Optional<MovieView> findByMovieAndViewDate(Movie movie, LocalDate viewDate);

    @Query("SELECT mv.movie, CAST(SUM(mv.views) AS int) as totalViews " +
            "FROM MovieView mv " +
            "WHERE mv.viewDate BETWEEN :startDate AND :endDate " +
            "GROUP BY mv.movie " +
            "ORDER BY totalViews DESC")
    List<Object[]> findTopMoviesByViewsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM MovieView mv WHERE mv.viewDate < :cutoffDate")
    void deleteViewsOlderThanMonth(@Param("cutoffDate") LocalDate cutoffDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM MovieView mv WHERE mv.viewDate BETWEEN :startDate AND :endDate")
    void deleteMovieViewsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
