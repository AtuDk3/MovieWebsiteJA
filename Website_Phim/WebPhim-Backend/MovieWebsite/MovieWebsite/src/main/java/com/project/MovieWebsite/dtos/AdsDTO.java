
package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AdsDTO {

    @NotBlank(message = "email")
    private String email;

    @NotBlank(message = "Name ads required")
    private String name;

    @NotBlank(message = "Description ads required")
    private String description;

    @JsonProperty("number_days")
    private int numberDays;

    private double amount;

    @JsonProperty("is_active")
    private int isActive=1;

    @JsonProperty("is_confirm")
    private int isConfirm=0;

    @JsonProperty("list_img")
    private List<String> listImg;
}
