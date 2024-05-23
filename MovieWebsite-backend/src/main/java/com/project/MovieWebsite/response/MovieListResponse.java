package com.project.MovieWebsite.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MovieListResponse {
    private List<MovieResponse> movies;
    private int totalPages;
}
