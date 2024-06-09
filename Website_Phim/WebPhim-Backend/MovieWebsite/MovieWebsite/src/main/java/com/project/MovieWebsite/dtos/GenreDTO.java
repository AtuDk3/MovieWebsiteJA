package com.project.MovieWebsite.dtos;

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

    private String slug;

    @NotEmpty(message = "Description can not empty!")
    private String description;

    @NotEmpty(message = "Description can not empty!")
    private int isActive;
}
