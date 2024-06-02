package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Episode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeResponse {
    private int id;

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("episode")
    private int episode;

    @JsonProperty("movie_url")
    private String movieUrl;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("movie")
    private MovieResponse movieResponse;

    public static EpisodeResponse fromEpisode(Episode episode) {
        return EpisodeResponse.builder()
                .id(episode.getId())
                .movieId(episode.getMovie().getId())
                .movieUrl(episode.getMovieUrl())
                .createdAt(episode.getCreateAt())
                .updatedAt(episode.getUpdateAt())
                .episode(episode.getEpisode())
                .movieResponse(MovieResponse.fromMovie(episode.getMovie()))
                .build();
    }
}