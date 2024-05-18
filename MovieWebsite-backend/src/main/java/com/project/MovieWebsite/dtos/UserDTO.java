package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    @NotBlank(message = "Full name can not empty!")
    @JsonProperty("full_name")
    @Size(min = 5, message = "Full Name must have more than 3 character!")
    private String fullName;

    @NotBlank(message = "Phone number can not empty!")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password can not empty!")
    @JsonProperty("password")
    private String password;

    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId = "0";

    @JsonProperty("google_account_id")
    private String googleAccountId = "0";

    @NotNull(message = "Vip ID is required!")
    @JsonProperty("vip_id")
    private int userVIPId = 5;

    @NotNull(message = "Role ID is required!")
    @JsonProperty("role_id")
    private int roleId = 2;

    @JsonProperty("is_active")
    private int isActive = 1;



}
