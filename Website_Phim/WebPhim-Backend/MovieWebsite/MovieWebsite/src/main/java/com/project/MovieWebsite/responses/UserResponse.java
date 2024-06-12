package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.models.User;

import com.project.MovieWebsite.models.UserVIP;
import lombok.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.minidev.asm.ConvertDate.convertToDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse{

    @JsonProperty("id")
    private int id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("user_vip")
    private UserVIP userVip;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("img_avatar")
    private String imgAvatar;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;


    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder().
                id(user.getId()).
                fullName(user.getFullName()).
                phoneNumber(user.getPhoneNumber()).
                imgAvatar(user.getImgAvatar()).
                dob(user.getDob()).
                googleAccountId(user.getGoogleAccountId()).
                facebookAccountId(user.getFacebookAccountId()).
                createdAt(convertToDate(user.getCreateAt())).
                updatedAt(convertToDate(user.getUpdateAt())).
                userVip(user.getUserVip()).
                role(user.getRole()).
                email(user.getEmail()).
                isActive(user.getIsActive()).
                build();
        return userResponse;
    }

    public static List<UserResponse> fromListUser(List<User> listUser) {
        List<UserResponse> litsUserResponse = new ArrayList<>();
        for (User user: listUser){
                if(user.getRole().getName().equalsIgnoreCase("Admin")){
                    continue;
                }
                UserResponse userResponse= UserResponse.builder().
                        id(user.getId()).
                        fullName(user.getFullName()).
                        phoneNumber(user.getPhoneNumber()).
                        imgAvatar(user.getImgAvatar()).
                        dob(user.getDob()).
                        googleAccountId(user.getGoogleAccountId()).
                        facebookAccountId(user.getFacebookAccountId()).
                        createdAt(convertToDate(user.getCreateAt())).
                        updatedAt(convertToDate(user.getUpdateAt())).
                        userVip(user.getUserVip()).
                        role(user.getRole()).
                        email(user.getEmail()).
                        isActive(user.getIsActive()).
                        build();
                    litsUserResponse.add(userResponse);
                }
        return litsUserResponse;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

}
