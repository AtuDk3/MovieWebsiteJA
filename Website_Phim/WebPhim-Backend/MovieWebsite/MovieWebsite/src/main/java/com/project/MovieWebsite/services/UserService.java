package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    User getUserById(int userId) throws DataNotFoundException;

    List<User> getAllUser();

    User updateUser(int userId, UserDTO userDTO) throws DataNotFoundException;

    void deleteUser(int userId);

    void updatePassword(String phoneNumber, String newPassword);

    String login(String phoneNumber, String password) throws Exception;

    //public boolean verifyEmail(String token);
}
