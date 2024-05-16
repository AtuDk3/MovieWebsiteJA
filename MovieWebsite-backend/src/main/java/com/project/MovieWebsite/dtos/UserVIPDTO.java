package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserVIPDTO {
    @NotEmpty(message = "Name user VIP is not null!")
    private String name;

    @JsonProperty("number_month")
    @Min(value = 1, message = "Number month must be greater than or equal to 1!")
    private int numberMonth;

    @NotNull(message = "Price cannot be null!")
    @Min(value = 0, message = "Price must be greater than or equal to 0!")
    private float price;

}
