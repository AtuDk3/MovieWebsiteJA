package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
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
    public User updateUser(int userId, User user) {
        User existsUser = getUserById(userId);
        existsUser.setFullName(user.getFullName());
        existsUser.setPhoneNumber(user.getPhoneNumber());
        existsUser.setPassword(user.getPassword());
        existsUser.setDob(user.getDob());
        existsUser.setFacebookAccountId(user.getFacebookAccountId());
        existsUser.setGoogleAccountId(user.getGoogleAccountId());
        existsUser.setUserVIP(user.getUserVIP());
        existsUser.setRole(user.getRole());
        existsUser.setIsActive(user.getIsActive());
        return existsUser;
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
