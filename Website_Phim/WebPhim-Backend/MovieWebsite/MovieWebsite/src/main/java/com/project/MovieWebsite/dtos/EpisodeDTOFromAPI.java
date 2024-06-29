package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDTOFromAPI {

    @JsonProperty("name")
    private String name;

    @JsonProperty("link_embed")
    private String movieUrl;

}
