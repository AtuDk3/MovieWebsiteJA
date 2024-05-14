package com.project.MovieWebsite.services;

import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(int userId);

    List<User> getAllUser();

    User updateUser(int userId, User user);

    void deleteUser(int userId);
}
