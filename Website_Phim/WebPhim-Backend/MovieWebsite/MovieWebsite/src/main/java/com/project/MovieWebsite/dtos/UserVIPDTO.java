package com.project.MovieWebsite.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserVIPDTO {
    @NotEmpty(message = "Price can not empty!")
    @Min(value = 0, message = "Price must be greater than or equal to 0!")
    private float price;

}
