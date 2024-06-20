package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
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
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + "(:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%)")
    Page<Movie> searchMovies(
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m")
    Page<Movie> getAllMovies(Pageable pageable);



    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " m.genre.id = :genreId")
    Page<Movie> searchMoviesByGenreId(
            @Param("genreId") int genreId,
             Pageable pageable
    );

    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " YEAR(m.releaseDate) = :year")
    Page<Movie> searchMoviesByYear(
            @Param("year") int year,
            Pageable pageable
    );

    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " m.country.id = :countryId")
    Page<Movie> searchMoviesByCountryId(
            @Param("countryId") int countryId,
            Pageable pageable
    );

    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " m.movieType.id = :movieTypeId")
    Page<Movie> searchMoviesByMovieTypeId(
            @Param("movieTypeId") int movieTypeId,
            Pageable pageable
    );

    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " (m.genre.id = :genreId or m.name like %:movieName%)"
            + " and m.id != :movieId")
    Page<Movie> searchMoviesRelated(
            @Param("movieId") int movieId,
            @Param("genreId") int genreId,
            @Param("movieName") String movieName,
            Pageable pageable
    );

    @Query("select m from Movie m where"
            + " m.isActive = 1 and"
            + " m.genre.isActive = 1 and"
            + " m.movieType.isActive = 1 and"
            + " m.country.isActive = 1 and"
            + " m.hot = 1")
    Page<Movie> searchHotMovies(
            Pageable pageable
    );

    @Query("SELECT DISTINCT YEAR(m.releaseDate) FROM Movie m ORDER BY YEAR(m.releaseDate)")
    List<Integer> findDistinctYears();

    Optional<List<Movie>> findByGenreId(int genreId);

    Optional<List<Movie>> findByCountryId(int countryId);

    Optional<List<Movie>> findByMovieTypeId(int movieTypeId);
}
