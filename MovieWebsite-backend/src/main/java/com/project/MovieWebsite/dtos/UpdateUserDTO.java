package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateUserDTO {

    @JsonProperty("full_name")
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

    @JsonProperty("img_avatar")
    private String imgAvatar;

}