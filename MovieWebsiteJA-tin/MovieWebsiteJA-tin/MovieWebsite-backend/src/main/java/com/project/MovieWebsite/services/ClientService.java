package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.models.User;

public interface ClientService {
    String create(UserDTO userDTO);

    String forgot_password(User user);

    String authenticate_account(String name, String email);

    void sendTradingCode(String tradingCode, String email);

    void sendAdsExpiration(String email);

    void sendAdsSuccess(String registrationDate, String expiryDate, String email);
}