package com.project.MovieWebsite.responses;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MovieListResponse {

    private List<MovieResponse> movies;
    private int totalPages;
}
