package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.MovieType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MovieTypeResponse {

    private int id;

    private String name;

    @JsonProperty("is_active")
    private int isActive;

    public static MovieTypeResponse fromMovieType(MovieType movieType){
        MovieTypeResponse movieTypeResponseResponse = MovieTypeResponse.builder()
                .id(movieType.getId())
                .name(movieType.getName())
                .isActive(movieType.getIsActive())
                .build();
        return movieTypeResponseResponse;
    }

    public static List<MovieTypeResponse> fromListMovieType(List<MovieType> listMovieType){
        List<MovieTypeResponse> listMovieTypeResponse= new ArrayList<>();
        for(MovieType movieType: listMovieType) {
            MovieTypeResponse movieTypeResponseResponse = MovieTypeResponse.builder()
                    .id(movieType.getId())
                    .name(movieType.getName())
                    .isActive(movieType.getIsActive())
                    .build();
            listMovieTypeResponse.add(movieTypeResponseResponse);
        }
        return listMovieTypeResponse;
    }
}
