package com.project.MovieWebsite.responses;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.Genre;

import com.project.MovieWebsite.models.Movie;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FavouriteResponse {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("movie_name")
    private String movieName;

    private String image;

    @JsonProperty("genre")
    private Genre genre;

    @JsonProperty("genre_name")
    private String genreName;



    public static List<FavouriteResponse> fromFavourite(List<Favourite> lists){
        List<FavouriteResponse> listFavouriteResponse = new ArrayList<>();
        for (Favourite favourite: lists){
            FavouriteResponse favouriteResponse= FavouriteResponse.builder()
                    .movieId(favourite.getMovie().getId())
                    .movieName(favourite.getMovie().getName())
                    .image(favourite.getMovie().getImage())
                    .genre(favourite.getMovie().getGenre())
                    .build();
            listFavouriteResponse.add(favouriteResponse);
        }
        return listFavouriteResponse;
    }


    public static List<FavouriteResponse> fromMovie(List<MovieResponse> lists){
        List<FavouriteResponse> listFavouriteResponse = new ArrayList<>();
        for (MovieResponse movie: lists){
            FavouriteResponse favouriteResponse= FavouriteResponse.builder()
                    .movieId(movie.getId())
                    .movieName(movie.getName())
                    .image(movie.getImage())
                    .genreName(movie.getGenreName())
                    .build();
            listFavouriteResponse.add(favouriteResponse);
        }
        return listFavouriteResponse;
    }
}
