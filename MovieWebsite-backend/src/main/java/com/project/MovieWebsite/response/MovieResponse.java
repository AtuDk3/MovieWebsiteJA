package com.project.MovieWebsite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Movie;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MovieResponse extends BaseResponse {
    private  int id;

    private String name;

    private String description;

    private String image;

    private String slug;

    @JsonProperty("release_date")
    private Date releaseDate;

    private String duration;

    @JsonProperty("id_genre")
    private int idGenre;

    @JsonProperty("id_movie_type")
    private int idMovieType;

    @JsonProperty("id_country")
    private int idCountry;

    private int episode;

    @JsonProperty("is_fee")
    private int isFee;

    private int hot;

    private int season;

    @JsonProperty("limited_age")
    private int limitedAge;

    @JsonProperty("movie_type_name")
    private String movieTypeName;

    @JsonProperty("genre_name")
    private String genreName;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("number_views")
    private int numberViews;

    public  static MovieResponse fromMovie(Movie movie){
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
    }
}
