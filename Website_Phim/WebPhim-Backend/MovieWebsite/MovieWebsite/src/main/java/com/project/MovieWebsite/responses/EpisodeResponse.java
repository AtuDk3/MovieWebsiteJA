package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Episode;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("movie_name")
    private String movieName;

    @JsonProperty("episode")
    private int episode;

    @JsonProperty("movie_url")
    private String movieUrl;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

//    @JsonProperty("movie")
//    private MovieResponse movieResponse;

//    public static EpisodeResponse fromEpisode(Episode episode) {
//        return EpisodeResponse.builder()
//                .movieName(episode.getMovie().getName())
//                .movieUrl(episode.getMovieUrl())
//                .createdAt(episode.getCreateAt())
//                .updatedAt(episode.getUpdateAt())
//                .episode(episode.getEpisode())
//                //.movieResponse(MovieResponse.fromMovie(episode.getMovie()))
//                .build();
//    }

    public static List<EpisodeResponse> fromListEpisode(List<Episode> listEpisode) {

        List<EpisodeResponse> listEpisodeResponse= new ArrayList<>();
        for(Episode episode: listEpisode){
            EpisodeResponse episodeResponse= EpisodeResponse.builder()
                    .id(episode.getId())
                    .movieName(episode.getMovie().getName())
                    .movieUrl(episode.getMovieUrl())
                    .createdAt(convertToDate(episode.getCreateAt()))
                    .updatedAt(convertToDate(episode.getUpdateAt()))
                    .episode(episode.getEpisode())
                    .build();
            listEpisodeResponse.add(episodeResponse);
        }
        return listEpisodeResponse;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}