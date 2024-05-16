package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GenreDTO {
    @NotEmpty(message = "Name can not empty!")
    private String name;

    @NotEmpty(message = "Description can not empty!")
    private String description;

    private String slug;

    @JsonProperty("is_active")
    private int isActive = 1;
}
