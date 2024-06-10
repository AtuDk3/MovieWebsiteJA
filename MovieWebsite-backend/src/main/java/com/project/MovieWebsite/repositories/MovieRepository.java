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

//    @Query("select m from Movie m where"
//            + "(:genreId is null or :genreId = 0 or m.genre.id = :genreId) and"
//            + "(:countryId is null or :countryId = 0 or m.country.id = :countryId)"
//            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%)")
//    Page<Movie> searchMovies(
//            @Param("genreId") int genreId,
//            @Param("countryId") int countryId,
//            @Param("keyword") String keyword, Pageable pageable
//    );

    @Query("select m from Movie m where"
            + "(:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%)")
    Page<Movie> searchMovies(
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m where"
            + "(:genreId is null or :genreId = 0 or m.genre.id = :genreId)"
            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%) and m.isActive = 1")
    Page<Movie> searchMoviesByGenreId(
            @Param("genreId") int genreId,
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m where"
            + "(:countryId is null or :countryId = 0 or m.country.id = :countryId)"
            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%) and m.isActive = 1")
    Page<Movie> searchMoviesByCountryId(
            @Param("countryId") int countryId,
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m where"
            + "(:movieTypeId is null or :movieTypeId = 0 or m.movieType.id = :movieTypeId)"
            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%) and m.isActive = 1")
    Page<Movie> searchMoviesByMovieTypeId(
            @Param("movieTypeId") int movieTypeId,
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m where"
            + "(:movieId is null or :movieId = 0 or m.id = :movieId) "
            + "and (:keyword is null or :keyword = '' or m.name like %:keyword% or m.description like %:keyword%) and m.isActive = 1 order by m.numberView desc")
    Page<Movie> searchMovieRelated(
            @Param("movieId") int movieId,
            @Param("keyword") String keyword, Pageable pageable
    );

    @Query("select m from Movie m where m.hot = 1 and m.isActive = 1")
    Page<Movie> searchHotMovies(
            Pageable pageable
    );

    @Query("SELECT m FROM Movie m WHERE "
            + "(m.genre.id = :genreId "
            + "or :movieName is null or :movieName = '' or m.name like %:movieName%)"
            +"AND m.id != :movieId ")
    Page<Movie> searchMoviesRelated(
            @Param("movieId") int movieId,
            @Param("genreId") int genreId,
            @Param("movieName") String movieName,
            Pageable pageable
    );

    Optional<List<Movie>> findByGenreId(int genreId);

    Optional<List<Movie>> findByCountryId(int countryId);

    Optional<List<Movie>> findByMovieTypeId(int movieTypeId);

}
