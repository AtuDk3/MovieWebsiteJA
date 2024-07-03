package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.models.Rate;
import com.project.MovieWebsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Integer> {

    Rate findByMovieAndUser(Movie movie, User user);

    List<Rate> findAllByMovie(Movie movie);

    @Modifying
    @Transactional
    @Query("DELETE FROM Rate r WHERE r.rateDate < :cutoffDate")
    void deleteRateOlderThanMonth(@Param("cutoffDate") LocalDate cutoffDate);
}