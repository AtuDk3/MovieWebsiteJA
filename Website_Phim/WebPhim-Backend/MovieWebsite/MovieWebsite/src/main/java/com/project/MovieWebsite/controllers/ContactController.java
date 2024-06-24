package com.project.MovieWebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;

    private String host = "imap.gmail.com";
    private String username = "webphimm@gmail.com";
    private String password = "ampg tgio aebn vqls";

    // Sử dụng HashSet để lưu trữ ID của các email đã phản hồi
    private Set<String> repliedEmailIds = new HashSet<>();

    @Scheduled(fixedRate = 60000) // Kiểm tra email mỗi 60 giây
    public void checkEmail() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                if (!message.isSet(Flags.Flag.SEEN)) {
                    String messageId = ((MimeMessage) message).getMessageID();
                    if (!repliedEmailIds.contains(messageId)) {
                        processNewEmail(message);
                        message.setFlag(Flags.Flag.SEEN, true);
                        repliedEmailIds.add(messageId);
                    }
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processNewEmail(Message message) {
        try {
            String subject = message.getSubject();
            String from = ((MimeMessage) message).getFrom()[0].toString();
            String body = message.getContent().toString();

            sendSimpleMessage(from, "Re: " + subject, "Thank you for your email. We will get back to you shortly.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
