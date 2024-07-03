package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType, Integer> {
}
