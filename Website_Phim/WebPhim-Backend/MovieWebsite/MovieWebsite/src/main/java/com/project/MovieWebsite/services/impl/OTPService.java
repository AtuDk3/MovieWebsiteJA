package com.project.MovieWebsite.services.impl;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.project.MovieWebsite.configurations.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OTPService {

    private static final Logger logger = LoggerFactory.getLogger(OTPService.class);
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new SecureRandom();

    @Autowired
    private TwilioConfig twilioConfig= new TwilioConfig();

    public String generateOtp(String phoneNumber) {

        String otp = String.format("%04d", random.nextInt(10000));
        otpStorage.put(phoneNumber, otp);
        sendOtpMessage(phoneNumber, otp);
        return otp;
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        return otp.equals(otpStorage.get(phoneNumber));
    }

    private void sendOtpMessage(String phoneNumber, String otp) {
        try {
            Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber("+17085728487"),
                    "Your OTP is: " + otp).create();
            logger.info("OTP sent successfully to {}", phoneNumber);
        } catch (Exception e) {
            logger.error("Failed to send OTP to {}: {}", phoneNumber, e.getMessage());
            throw e;
        }
    }
}
