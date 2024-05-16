package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UserVIPDTO;
import com.project.MovieWebsite.models.UserVIP;

import java.util.List;

public interface UserVIPService {
    UserVIP createUserVIP(UserVIPDTO userVIPDTO);

    UserVIP getUserVIPById(int userVipId);

    List<UserVIP> getAllUserVIP();

    UserVIP updateUserVIP(int userVipId, UserVIPDTO userVIPDTO);

    void deleteUserVIP(int userVipId);
}
