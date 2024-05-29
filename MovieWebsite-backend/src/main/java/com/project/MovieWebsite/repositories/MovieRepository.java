package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    boolean existsByName(String name);

    Page<Movie> findAll(Pageable pageable);

    @Query("select m from Movie m where"
            + "(:genreId is null or :genreId = 0 or m.genre.id = :genreId)"
            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%)")
    Page<Movie> searchMovies(
            @Param("genreId") int genreId,
            @Param("keyword") String keyword, Pageable pageable
    );

    Optional<List<Movie>> findByGenreId(int genreId);

    Optional<List<Movie>> findByCountryId(int countryId);

}
