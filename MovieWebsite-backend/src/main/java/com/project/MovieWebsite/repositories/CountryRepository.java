package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
