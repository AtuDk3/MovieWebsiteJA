package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.DataMailDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.services.ClientService;
import com.project.MovieWebsite.services.MailService;
import com.project.MovieWebsite.utils.Const;
import com.project.MovieWebsite.utils.DataUtils;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private MailService mailService;

    @Override
    public String create(UserDTO userDTO) {
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(userDTO.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("name", userDTO.getFullName());
            String password= DataUtils.generateTempPwd(6);
            props.put("password", password);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return password;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public String forgot_password(User user) {
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(user.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_FORGOT_PASSWORD.FORGOT_PASSWORD);
            Map<String, Object> props = new HashMap<>();
            String otp= DataUtils.generateTempPwd(6);
            props.put("name", user.getFullName());
            props.put("otp", otp);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_FORGOT_PASSWORD.FORGOT_PASSWORD);
            return otp;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public String authenticate_account(String name, String email) {
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(email);
            dataMail.setSubject(Const.SEND_MAIL_FORGOT_PASSWORD.FORGOT_PASSWORD);
            Map<String, Object> props = new HashMap<>();
            String otp= DataUtils.generateTempPwd(6);
            props.put("name", name);
            props.put("otp", otp);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_FORGOT_PASSWORD.FORGOT_PASSWORD);
            return otp;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendTradingCode(String tradingCode, String email) {
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(email);
            dataMail.setSubject(Const.SEND_MAIL_TRADING_CODE.TRADING_CODE);
            Map<String, Object> props = new HashMap<>();
            props.put("trading_code", tradingCode);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_TRADING_CODE.TRADING_CODE);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void sendAdsExpiration(String email) {
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(email);
            dataMail.setSubject(Const.SEND_ADS_EXPIRATION.ADS_EXPIRATION);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_ADS_EXPIRATION.ADS_EXPIRATION);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void sendAdsSuccess(String registrationDate, String expiryDate, String email) {
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(email);
            dataMail.setSubject(Const.SEND_ADS_SUCCESS.ADS_SUCCESS);
            Map<String, Object> props = new HashMap<>();
            props.put("registration_date", registrationDate);
            props.put("expiry_date", expiryDate);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_ADS_SUCCESS.ADS_SUCCESS);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }

    }
}

