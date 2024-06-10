package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("is_active")
    private int isActive = 1;
}
