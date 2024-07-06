package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByName(String name);

    Optional<Country> findById(int countryId);

    Country findByName(String name);
}