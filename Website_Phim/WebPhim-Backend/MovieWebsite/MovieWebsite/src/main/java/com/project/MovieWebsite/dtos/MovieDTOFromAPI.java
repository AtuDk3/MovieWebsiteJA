package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTOFromAPI {

    @NotBlank(message = "Name movie required")
    private String name;

    @JsonProperty("content")
    private String description;

    @JsonProperty("thumb_url")
    private String image;

    private String slug;

    @JsonProperty("release_date")
    private Date releaseDate= new Date();

    @JsonProperty("time")
    private String duration;

    @JsonProperty("category")
    private List<GenreDTO> genreDTOList;

    @JsonProperty("type")
    private String type;

    @JsonProperty("country")
    private List<CountryDTO> countryDTOList;

    @JsonProperty("episode_total")
    private String episode;

    @JsonProperty("chieurap")
    private String chieurap;

    @JsonProperty("is_fee")
    private int isFee=0;

    private int hot=0;

    @JsonProperty("tmdb")
    private SeasonDTO seasonDTO;

    @Min(value=13, message = "Limited age must have more than 13")
    @JsonProperty("limited_age")
    private int limitedAge=14;

    @JsonProperty("is_active")
    private int isActive=1;
}
