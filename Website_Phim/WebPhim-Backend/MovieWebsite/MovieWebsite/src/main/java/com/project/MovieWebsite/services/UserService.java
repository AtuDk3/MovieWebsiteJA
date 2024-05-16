package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    User getUserById(int userId);

    List<User> getAllUser();

    User updateUser(int userId, UserDTO userDTO) throws DataNotFoundException;

    void deleteUser(int userId);

    String login(String phoneNumber, String password) throws Exception;
}
