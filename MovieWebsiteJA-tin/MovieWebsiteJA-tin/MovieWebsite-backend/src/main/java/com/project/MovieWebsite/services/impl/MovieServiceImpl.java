package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.*;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.*;
import com.project.MovieWebsite.repositories.*;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieTypeRepository movieTypeRepository;
    private  final CountryRepository countryRepository;
    private final EpisodeRepository episodeRepository;
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
                .isActive(movieDTO.getIsActive())
                .build();
        return movieRepository.save(newMovie);
    }

    @Override
    public Movie createMovieFromAPI(MovieDTOFromAPI movieDTO, List<ListEpisodeDTOFromAPI> listEpisodeDTOFromAPI) throws DataNotFoundException{

        Movie movie= movieRepository.findByName(movieDTO.getName());


        if (movie == null) {
            MovieType existingMovieType;
            if (movieDTO.getChieurap().equals("true")) {
                existingMovieType = movieTypeRepository.findByName("Phim Chiếu Rạp");
                movieDTO.setIsFee(1);
            } else {
                if (movieDTO.getType().equalsIgnoreCase("single")) {
                    existingMovieType = movieTypeRepository.findByName("Phim Lẻ");
                } else {
                    existingMovieType = movieTypeRepository.findByName("Phim Bộ");
                }
            }
            if (!countryRepository.existsByName(movieDTO.getCountryDTOList().get(0).getName())) {
                Country newCountry = Country.builder()
                        .name(movieDTO.getCountryDTOList().get(0).getName())
                        .isActive(1)
                        .build();
                countryRepository.save(newCountry);
            }
            Country existingCountry = countryRepository.findByName(movieDTO.getCountryDTOList().get(0).getName());

            if (!genreRepository.existsByName(movieDTO.getGenreDTOList().get(0).getName())) {
                Genre newGenre = Genre.builder()
                        .name(movieDTO.getGenreDTOList().get(0).getName())
                        .description(movieDTO.getGenreDTOList().get(0).getName())
                        .slug("")
                        .isActive(1)
                        .build();
                genreRepository.save(newGenre);
            }
            Genre existingGenre = genreRepository.findByName(movieDTO.getGenreDTOList().get(0).getName());

            Integer season = movieDTO.getSeasonDTO().getSeason();
            if (season == null) {
                season = 1;
            }
            Movie newMovie = Movie.builder()
                    .name(movieDTO.getName())
                    .description(movieDTO.getDescription())
                    .image(movieDTO.getImage())
                    .slug(movieDTO.getSlug())
                    .releaseDate(movieDTO.getReleaseDate())
                    .duration(movieDTO.getDuration())
                    .genre(existingGenre)
                    .movieType(existingMovieType)
                    .country(existingCountry)
                    .episode(Integer.parseInt(movieDTO.getEpisode().split(" ")[0]))
                    .hot(movieDTO.getHot())
                    .isFee(movieDTO.getIsFee())
                    .season(season)
                    .limitedAge(movieDTO.getLimitedAge())
                    .isActive(movieDTO.getIsActive())
                    .build();
            movie = movieRepository.save(newMovie);
        }
        for (EpisodeDTOFromAPI episodeDTOFromAPI : listEpisodeDTOFromAPI.get(0).getEpisodeDTOList()) {
            if (episodeDTOFromAPI.getName().equalsIgnoreCase("Full")) {
                episodeDTOFromAPI.setName("1");
            }
            if (!episodeRepository.existsByMovieAndEpisode(movie, Integer.parseInt(episodeDTOFromAPI.getName()))) {
                Episode newEpisode = Episode.builder()
                        .movie(movie)
                        .episode(Integer.parseInt(episodeDTOFromAPI.getName()))
                        .movieUrl(episodeDTOFromAPI.getMovieUrl())
                        .build();
                episodeRepository.save(newEpisode);
            }

        }

        return movie;
    }

    @Override
    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) throws Exception {
        return movieRepository.findByGenreId(genreId)
                .orElseThrow(() -> new Exception("No movies found for this genre"));
    }

    @Override
    public List<Movie> getMoviesByCountryId(int countryId) throws Exception {
        return movieRepository.findByCountryId(countryId)
                .orElseThrow(() -> new Exception("No movies found for this country"));
    }

    @Override
    public List<Movie> getMovieByMovieTypeId(int movieTypeId) throws Exception {
        return movieRepository.findByMovieTypeId(movieTypeId)
                .orElseThrow(() -> new Exception("No movies found for this movie type"));
    }

//    @Override
//    public Page<MovieResponse> getAllMovies(String keyword, int genreId, int countryId, PageRequest pageRequest) {
//        Page<Movie> moviesPage = movieRepository.searchMovies(genreId, countryId, keyword, pageRequest);
//        return mapToMovieResponsePage(moviesPage);
//    }

    @Override
    public Page<MovieResponse> getAllSearchMovies(String keyword, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMovies(keyword, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    //admin list movie
    @Override
    public Page<MovieResponse> getAllMovies(String keyword, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.getAllMovies(pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByGenreId(String keyword, int genreId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByGenreId(genreId,pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByYear(int year, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByYear(year,pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesToday(PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.getAllMoviesToday(LocalDateTime.now(), pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByCountryId(String keyword, int countryId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByCountryId(countryId,pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesByMovieTypeId(String keyword, int movieTypeId, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesByMovieTypeId(movieTypeId,pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getAllMoviesRelated(int movieId, int genreId, String movieName, PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchMoviesRelated(movieId,genreId, movieName, pageRequest);
        return mapToMovieResponsePage(moviesPage);
    }

    @Override
    public Page<MovieResponse> getHotMovies(PageRequest pageRequest) {
        Page<Movie> moviesPage = movieRepository.searchHotMovies(pageRequest);
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
                    .isActive(movie.getIsActive())
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
//            existingMovie.setImage(movieDTO.getImage());
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
            existingMovie.setIsActive(movieDTO.getIsActive());
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

    @Override
    public List<Integer> getDistinctYears() {
        return movieRepository.findDistinctYears();
    }


}