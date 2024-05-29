package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UpdateUserDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    User getUserById(int userId) throws DataNotFoundException;

    List<User> getAllUser();

    User updateUser(int userId, UpdateUserDTO userDTO) throws DataNotFoundException;

    void deleteUser(int userId);

    void updatePassword(String phoneNumber, String newPassword);

    String login(String phoneNumber, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    boolean checkCurrentPassword(int userId, String passwordCheck) throws Exception;
    //public boolean verifyEmail(String token);
}
