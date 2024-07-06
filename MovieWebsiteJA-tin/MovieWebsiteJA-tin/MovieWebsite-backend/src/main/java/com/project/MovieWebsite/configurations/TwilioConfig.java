package com.project.MovieWebsite.configurations;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    public String accountSid;

    @Value("${twilio.auth-token}")
    public String authToken;

    @Value("${twilio.phone-number}")
    public String phoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}