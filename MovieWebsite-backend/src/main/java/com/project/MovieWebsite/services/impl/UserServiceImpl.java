package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.UserVIP;
import com.project.MovieWebsite.repositories.RoleRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.repositories.UserVIPRepository;
import com.project.MovieWebsite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserVIPRepository userVIPRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException{
        String phoneNumber = userDTO.getPhoneNumber();

        User existingUser = userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new DataNotFoundException("Existing phone number!"));

        Role existingRole= roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getRoleId()));

        UserVIP existingUserVip= userVIPRepository.findById(userDTO.getUserVIPId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getUserVIPId()));

        User newUser = User.builder().fullName(userDTO.getFullName()).
                phoneNumber(userDTO.getPhoneNumber()).
                password(userDTO.getPassword()).
                dob(userDTO.getDob()).
                facebookAccountId(userDTO.getFacebookAccountId()).
                googleAccountId(userDTO.getGoogleAccountId()).
                userVIP(existingUserVip).
                role(existingRole).
                isActive(userDTO.getIsActive()).
                build();

        if (userDTO.getFacebookAccountId().equals("0") || userDTO.getGoogleAccountId().equals("0")){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int userId, UserDTO userDTO) throws DataNotFoundException{
        Role existingRole= roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getRoleId()));
        UserVIP existingUserVip= userVIPRepository.findById(userDTO.getUserVIPId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getUserVIPId()));

        User existsUser = getUserById(userId);
        existsUser.setFullName(userDTO.getFullName());
        existsUser.setPhoneNumber(userDTO.getPhoneNumber());
        existsUser.setPassword(userDTO.getPassword());
        existsUser.setDob(userDTO.getDob());
        existsUser.setFacebookAccountId(userDTO.getFacebookAccountId());
        existsUser.setGoogleAccountId(userDTO.getGoogleAccountId());
        existsUser.setUserVIP(existingUserVip);
        existsUser.setRole(existingRole);
        existsUser.setIsActive(userDTO.getIsActive());
        return existsUser;
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }
}
