package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse extends BaseResponse{

    private String name;
    private String description;
    private String image;
    private String slug;
    private Date releaseDate;
    private String duration;
    private int idGenre;
    private int idMovieType;
    private int idCountry;
    private int episode;
    private int isFee;
    private int hot;
    private int season;
    private int limitedAge;
    private String movieTypeName;

}
