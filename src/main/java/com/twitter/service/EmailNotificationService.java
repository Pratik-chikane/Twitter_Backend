package com.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.twitter.dto.EMailMessage;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotification(String to, String subject, String body) {
//    	 String to = "user@example.com";
//         String subject = "Subscription Purchase Success";
//         String body = "Thank you for purchasing a subscription. Your payment was successful.";
//    	 emailService.sendNotification(to, subject, body);
//    	  SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
//        javaMailSender.send(message);
    }
}