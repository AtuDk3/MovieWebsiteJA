package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    boolean existsByName(String name);
}
