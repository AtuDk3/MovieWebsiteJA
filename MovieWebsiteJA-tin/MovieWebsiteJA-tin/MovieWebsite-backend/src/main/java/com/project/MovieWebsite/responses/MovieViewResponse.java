package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieView;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieViewResponse {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("movie_name")
    private String movieName;

    private String image;

    @JsonProperty("movie_view")
    private int movieView;

    public static List<MovieViewResponse> fromMovieView(List<MovieView> lists){
        List<MovieViewResponse> listMovieViewResponse = new ArrayList<>();
        for (MovieView movieView: lists){
            MovieViewResponse movieViewResponse= MovieViewResponse.builder()
                    .movieId(movieView.getMovie().getId())
                    .movieName(movieView.getMovie().getName())
                    .image(movieView.getMovie().getImage())
                    .movieView(movieView.getViews())
                    .build();
            listMovieViewResponse.add(movieViewResponse);
        }
        return listMovieViewResponse;
    }


    public static List<MovieViewResponse> fromMovie(List<Movie> lists){
        List<MovieViewResponse> listMovieViewResponse = new ArrayList<>();
        for (Movie movie: lists){
            MovieViewResponse movieViewResponse= MovieViewResponse.builder()
                    .movieId(movie.getId())
                    .movieName(movie.getName())
                    .image(movie.getImage())
                    .movieView(movie.getNumberView())
                    .build();
            listMovieViewResponse.add(movieViewResponse);
            if(listMovieViewResponse.size()==10){
                break;
            }
        }
        return listMovieViewResponse;
    }
}
