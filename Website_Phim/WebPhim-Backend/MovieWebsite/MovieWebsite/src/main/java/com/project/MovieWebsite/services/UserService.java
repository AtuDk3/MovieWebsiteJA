package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UpdateUserDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.exceptions.MailErrorExeption;
import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO) ;

    void checkAccount(UserDTO userDTO) throws DataNotFoundException, MailErrorExeption;

    User getUserById(int userId) throws DataNotFoundException;

    List<User> getAllUser();

    User updateUser(int userId, UpdateUserDTO userDTO) throws DataNotFoundException;

    void deleteUser(int userId);

    User updatePassword(String phoneNumber, String newPassword);

    String login(String phoneNumber, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    boolean checkCurrentPassword(int userId, String passwordCheck) throws Exception;

}
