package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UpdateUserDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.dtos.UserLoginGGDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.exceptions.MailErrorExeption;
import com.project.MovieWebsite.models.User;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO) ;

    User createUserFromLoginGG(UserLoginGGDTO userLoginGGDTO) ;

    void checkAccount(UserDTO userDTO) throws DataNotFoundException, MailErrorExeption;

    User getUserById(int userId) throws DataNotFoundException;

    List<User> getAllUser();

    User updateUser(int userId, UpdateUserDTO userDTO) throws DataNotFoundException;

    void ban_account(int userId) throws DataNotFoundException;

    void unban_account(int userId) throws DataNotFoundException;

    User updatePassword(String phoneNumber, String newPassword);

    String login(String phoneNumber, String password) throws Exception;

    String loginGG(UserLoginGGDTO userLoginGGDTO) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    boolean checkCurrentPassword(int userId, String passwordCheck) throws Exception;

}