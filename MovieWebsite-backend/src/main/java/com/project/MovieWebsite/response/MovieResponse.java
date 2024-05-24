package com.project.MovieWebsite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MovieResponse extends BaseResponse {
    private String name;

    private String description;

    private String image;

    private String slug;

    @JsonProperty("release_date")
    private LocalDateTime releaseDate;

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
}
