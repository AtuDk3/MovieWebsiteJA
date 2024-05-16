package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.UserVIPDTO;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.UserVIP;
import com.project.MovieWebsite.repositories.UserVIPRepository;
import com.project.MovieWebsite.services.UserVIPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class UserVIPServiceImpl implements UserVIPService {
    private final UserVIPRepository userVIPRepository;

    @Override
    public UserVIP createUserVIP(UserVIPDTO userVIPDTO) {
        UserVIP newUserVIP = UserVIP.builder().price(userVIPDTO.getPrice()).numberMonth(userVIPDTO.getNumberMonth()).build();
        return userVIPRepository.save(newUserVIP);
    }

    @Override
    public UserVIP getUserVIPById(int userVipId) {
        return userVIPRepository.findById(userVipId).orElseThrow(() -> new RuntimeException("UserVIP not found!"));
    }

    @Override
    public List<UserVIP> getAllUserVIP() {
        return userVIPRepository.findAll();
    }

    @Override
    public UserVIP updateUserVIP(int userVipId, UserVIPDTO userVIPDTO) {
        UserVIP existsUserVIP = getUserVIPById(userVipId);
        //existsUserVIP.setNumberMonth(userVIPDTO.getNumberMonth());
        existsUserVIP.setPrice(userVIPDTO.getPrice());
        existsUserVIP.setName(userVIPDTO.getName());
        userVIPRepository.save(existsUserVIP);
        return existsUserVIP;
    }

    @Override
    public void deleteUserVIP(int userVipId) {
        userVIPRepository.deleteById(userVipId);
    }
}
