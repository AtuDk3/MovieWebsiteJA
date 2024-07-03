package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.MovieViewExecuted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface MovieViewExecutedRepository extends JpaRepository<MovieViewExecuted, Integer> {

    MovieViewExecuted findByDateExecuted(LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM MovieViewExecuted mv WHERE mv.dateExecuted < :startDate")
    void deleteMovieViewsBetweenDates(@Param("startDate") LocalDate startDate);
}