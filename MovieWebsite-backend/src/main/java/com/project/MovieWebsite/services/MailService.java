package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.DataMailDTO;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}

