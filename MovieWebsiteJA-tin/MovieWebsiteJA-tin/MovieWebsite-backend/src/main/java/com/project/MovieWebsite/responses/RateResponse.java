package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Rate;
import com.project.MovieWebsite.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateResponse {

    @JsonProperty("number_rate")
    private int numberRate;

    @JsonProperty("average_star")
    private float averageStar;

    @JsonProperty("movie_id")
    private int movieId;

    public static RateResponse fromRate(Movie movie) {
        RateResponse rateResponse = RateResponse.builder().
                numberRate(movie.getNumberRate()).
                averageStar(movie.getAverageStar()).
                movieId(movie.getId()).
                build();
        return rateResponse;
    }
}
