package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.MovieViewExecuted;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MovieViewExecutedRepository extends JpaRepository<MovieViewExecuted, Integer> {

    MovieViewExecuted findByDateExecuted(LocalDate date);

}
