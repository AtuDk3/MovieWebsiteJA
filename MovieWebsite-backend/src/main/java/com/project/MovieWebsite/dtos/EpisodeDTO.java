
package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EpisodeDTO {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("episode")
    private int episode = 1;

    @JsonProperty("movie_url")
    @NotBlank(message = "Movie url required")
    private String movieUrl;


}
