package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginDTO {

    @NotBlank(message = "Phone number can not empty!")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password can not empty!")
    @JsonProperty("password")
    private String password;

}
