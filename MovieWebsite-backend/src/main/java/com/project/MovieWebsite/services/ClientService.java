package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.models.User;

public interface ClientService {
    String create(UserDTO userDTO);

    String forgot_password(User user);
}