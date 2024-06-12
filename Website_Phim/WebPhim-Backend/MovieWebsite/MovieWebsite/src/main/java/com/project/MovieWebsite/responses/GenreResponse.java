package com.project.MovieWebsite.responses;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.Genre;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreResponse {

    private int id;

    private String name;

    private String slug;

    private String description;

    @JsonProperty("is_active")
    private int isActive = 1;

    public static GenreResponse fromGenre(Genre genre){
        GenreResponse genreResponse = GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .slug(genre.getSlug())
                .description(genre.getDescription())
                .isActive(genre.getIsActive())
                .build();
        return genreResponse;
    }

    public static List<GenreResponse> fromListGenre(List<Genre> listGenre){
        List<GenreResponse> listGenreResponse= new ArrayList<>();
        for(Genre genre: listGenre) {
            GenreResponse genreResponse = GenreResponse.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .slug(genre.getSlug())
                    .description(genre.getDescription())
                    .isActive(genre.getIsActive())
                    .build();
            listGenreResponse.add(genreResponse);
        }
        return listGenreResponse;
    }
}
