package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieViewDTO {

    @JsonProperty("movie_id")
    private int movieId;

    private int views;
}
