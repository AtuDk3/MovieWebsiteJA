package com.project.MovieWebsite.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    @NotBlank(message = "Name movie required")
    private String name;
}
