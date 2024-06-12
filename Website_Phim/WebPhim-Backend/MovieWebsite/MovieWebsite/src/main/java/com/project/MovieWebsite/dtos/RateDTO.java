package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("number_stars")
    @Min(value = 1, message = "Number star must be >=1")
    @Max(value = 5, message = "Number star must be <=5")
    private int numStars;

}
