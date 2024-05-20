package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoleDTO {
    @NotBlank(message = "Name can not empty!")
    @Size(min = 5, message = "Role must have more than 3 character!")
    private String name;
}
