package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.models.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends BaseResponse{

    @JsonProperty("id")
    private int id;

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
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("vip_id")
    private int vipId;

    @JsonProperty("role_id")
    private Role role;

    @JsonProperty("img_avatar")
    private String imgAvatar;

    @JsonProperty("is_active")
    private int isActive;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder().
                id(user.getId()).
                fullName(user.getFullName()).
                phoneNumber(user.getPhoneNumber()).
                //password(user.getPassword()).
                imgAvatar(user.getImgAvatar()).
                dob(user.getDob()).
                googleAccountId(user.getGoogleAccountId()).
                facebookAccountId(user.getFacebookAccountId()).
                vipId(user.getUserVip().getId()).
                role(user.getRole()).
                email(user.getEmail()).
                isActive(user.getIsActive()).
                build();
        return userResponse;
    }

}
