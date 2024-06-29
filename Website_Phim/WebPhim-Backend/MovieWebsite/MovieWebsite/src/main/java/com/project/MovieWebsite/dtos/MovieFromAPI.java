package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieFromAPI {

    @JsonProperty("movie")
    private MovieDTOFromAPI movie;

    @JsonProperty("episodes")
    private List<ListEpisodeDTOFromAPI> episodes;
}
