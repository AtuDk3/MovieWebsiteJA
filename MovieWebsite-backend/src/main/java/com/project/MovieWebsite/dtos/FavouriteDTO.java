package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FavouriteDTO {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("user_id")
    private int userId;

}