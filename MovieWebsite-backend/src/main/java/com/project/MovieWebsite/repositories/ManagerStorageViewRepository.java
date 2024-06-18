package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.ManagerStorageView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;


public interface ManagerStorageViewRepository extends JpaRepository<ManagerStorageView, Integer> {
    ManagerStorageView findByLastDelete(LocalDate lastDelete);

    @Query("select msv from ManagerStorageView msv ORDER BY msv.id DESC")
    ManagerStorageView getAll();
}