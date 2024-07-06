package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginGGDTO {
    @NotBlank(message = "Full name can not empty!")
    @JsonProperty("full_name")
    @Size(min = 5, message = "Full Name must have more than 3 character!")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId= "0";

    @JsonProperty("google_account_id")
    private String googleAccountId= "0";

    @JsonProperty("vip_id")
    private String vipName = "Normal User";

    @JsonProperty("role_id")
    private String roleName = "User";

    @JsonProperty("img_avatar")
    private String imgAvatar;

    @JsonProperty("is_active")
    private int isActive=1;
}