package com.jimmy.blogsystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

//邮件发送工具
@Component
public class MailUtils {

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String mailfrom;
    //发送简单邮件
    public void sendSimpleEmail(String mailto, String title, String content) throws Exception{
        //定制邮件发送内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailfrom);
        message.setTo(mailto);
        message.setSubject(title);
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

}
