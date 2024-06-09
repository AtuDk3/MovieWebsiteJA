package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.repositories.*;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieTypeRepository movieTypeRepository;
    private  final CountryRepository countryRepository;
    private  final GenreRepository genreRepository;

    @Override
    public Movie createMovie(MovieDTO movieDTO) throws DataNotFoundException{

        MovieType existingMovieType= movieTypeRepository.findById(movieDTO.getIdMovieType())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+movieDTO.getIdMovieType()));

        Country existingCountry= countryRepository.findById(movieDTO.getIdCountry())
                .orElseThrow(() -> new DataNotFoundException("Cannot find country with id: "+movieDTO.getIdCountry()));

        Genre existingGenre= genreRepository.findById(movieDTO.getIdGenre())
                .orElseThrow(() -> new DataNotFoundException("Cannot find country with id: "+movieDTO.getIdGenre()));

        movieDTO.init();
        Movie newMovie= Movie.builder()
                .name(movieDTO.getName())
                .description(movieDTO.getDescription())
                .image(movieDTO.getImage())
                .slug(movieDTO.getSlug())
                .releaseDate(movieDTO.getReleaseDate())
                .duration(movieDTO.getDuration())
                .genre(existingGenre)
                .movieType(existingMovieType)
                .country(existingCountry)
                .episode(movieDTO.getEpisode())
                .hot(movieDTO.getHot())
                .isFee(movieDTO.getIsFee())
                .season(movieDTO.getSeason())
                .limitedAge(movieDTO.getLimitedAge())
                .build();
        return movieRepository.save(newMovie);
    }

    @Override
    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }


    @Override
    public Page<MovieResponse> getAllMoviesByGenreId(String keyword, int genreId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByGenreId(genreId, keyword, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByCountryId(String keyword, int countryId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByCountryId(countryId, keyword, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByMovieTypeId(String keyword, int movieTypeId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByMovieTypeId(movieTypeId, keyword, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesRelated(int movieId, int genreId, String movieName, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesRelated(movieId,genreId, movieName, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    private Page<MovieResponse> mapToMovieResponsePage(Page<Movie> moviesPage) {
        return moviesPage.map(movie -> {
            MovieResponse movieResponse = MovieResponse.builder()
                    .id(movie.getId())
                    .name(movie.getName())
                    .description(movie.getDescription())
                    .image(movie.getImage())
                    .slug(movie.getSlug())
                    .releaseDate(movie.getReleaseDate())
                    .duration(movie.getDuration())
                    .idGenre(movie.getGenre().getId())
                    .idMovieType(movie.getMovieType().getId())
                    .idCountry(movie.getCountry().getId())
                    .episode(movie.getEpisode())
                    .hot(movie.getHot())
                    .isFee(movie.getIsFee())
                    .season(movie.getSeason())
                    .limitedAge(movie.getLimitedAge())
                    .movieTypeName(movie.getMovieType().getName())
                    .countryName(movie.getCountry().getName())
                    .genreName(movie.getGenre().getName())
                    .numberViews(movie.getNumberView())
                    .build();
            return movieResponse;
        });
    }

    @Override
    public Movie updateMovies(int id, MovieDTO movieDTO) throws Exception{
        Movie existingMovie= getMovieById(id);
        if(existingMovie != null){
            MovieType existingMovieType= movieTypeRepository.findById(movieDTO.getIdMovieType())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+movieDTO.getIdMovieType()));

            Country existingCountry= countryRepository.findById(movieDTO.getIdCountry())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find country with id: "+movieDTO.getIdCountry()));

            Genre existingGenre= genreRepository.findById(movieDTO.getIdGenre())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find country with id: "+movieDTO.getIdGenre()));

            existingMovie.setName(movieDTO.getName());
            existingMovie.setDescription(movieDTO.getDescription());
            existingMovie.setImage(movieDTO.getImage());
            existingMovie.setSlug(movieDTO.getSlug());
            existingMovie.setReleaseDate(movieDTO.getReleaseDate());
            existingMovie.setDuration(movieDTO.getDuration());
            existingMovie.setGenre(existingGenre);
            existingMovie.setMovieType(existingMovieType);
            existingMovie.setCountry(existingCountry);
            existingMovie.setEpisode(movieDTO.getEpisode());
            existingMovie.setHot(movieDTO.getHot());
            existingMovie.setIsFee(movieDTO.getIsFee());
            existingMovie.setSeason(movieDTO.getSeason());
            existingMovie.setLimitedAge(movieDTO.getLimitedAge());
            return movieRepository.save(existingMovie);
        }
        return null;
    }



    @Override
    public void deleteMovies(int id) {
        Optional<Movie> optionalMovie= movieRepository.findById(id);
        if(optionalMovie.isPresent()){
            movieRepository.deleteById(id);
        }
    }

    @Override
    public boolean existByName(String name) {
        return movieRepository.existsByName(name);
    }
}