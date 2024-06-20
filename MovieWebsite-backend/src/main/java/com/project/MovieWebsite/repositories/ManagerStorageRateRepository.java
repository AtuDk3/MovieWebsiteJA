package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.ManagerStorageRate;
import com.project.MovieWebsite.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ManagerStorageRateRepository extends JpaRepository<ManagerStorageRate, Integer> {
    ManagerStorageRate findByLastDelete(LocalDate lastDelete);

    @Query("select msr from ManagerStorageRate msr ORDER BY msr.id DESC")
    ManagerStorageRate getAll();
}
