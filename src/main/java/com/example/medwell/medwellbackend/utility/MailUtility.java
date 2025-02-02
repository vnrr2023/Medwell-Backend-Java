package com.example.medwell.medwellbackend.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

@Component
public class MailUtility {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine engine;

    @Async
    public void sendAppointmentSlotCreationMail(String emailId, Map<String, Object> data) throws Exception{
        Context context =new Context();
        context.setVariables(data);
        String htmlContent=engine.process("appointmentCreation",context);
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("csgptmail@gmail.com");
        helper.setTo(emailId);
        helper.setText(htmlContent,true);
        helper.setSubject("Regarding Appointment Creation for the Next Week");
        mailSender.send(mimeMessage);

    }

    @Async
    public void sendMarkettingEmails(String emailId,String htmlContent,String subject) throws Exception {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("csgptmail@gmail.com");
        helper.setTo(emailId);
        helper.setText(htmlContent,true);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }
}
