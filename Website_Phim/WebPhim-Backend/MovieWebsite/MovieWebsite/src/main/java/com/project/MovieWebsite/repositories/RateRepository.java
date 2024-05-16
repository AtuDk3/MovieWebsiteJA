package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Integer> {
}
