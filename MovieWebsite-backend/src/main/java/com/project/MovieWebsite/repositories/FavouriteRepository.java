package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    Favourite findByMovieId(int movieId);

    Optional<List<Favourite>> findByUserId(int genreId);

}