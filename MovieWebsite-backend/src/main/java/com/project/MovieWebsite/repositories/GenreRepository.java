package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    boolean existsByName(String name);

    Optional<Genre> findById(int genreId);
}
