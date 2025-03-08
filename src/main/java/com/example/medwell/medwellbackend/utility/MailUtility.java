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

    private void sendHtmlMail(String emailId,String subject,String htmlContent) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("csgptmail@gmail.com");
        helper.setTo(emailId);
        helper.setText(htmlContent,true);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendAppointmentSlotCreationMail(String emailId, Map<String, Object> data) throws Exception{
        Context context =new Context();
        context.setVariables(data);
        String htmlContent=engine.process("appointmentCreation",context);
        sendHtmlMail(emailId,"Regarding Appointment Creation for the Next Week",htmlContent);

    }

    @Async
    public void sendMarkettingEmails(String emailId,String htmlContent,String subject) throws Exception {
        sendHtmlMail(emailId,subject,htmlContent);
    }

    @Async
    public void sendReminderMail(String email,String subject,String doctorName,String patientName,String address,String appointmentTime,double lat,double lon) throws MessagingException {
        Context context = new Context();
        context.setVariables(Map.of(
                "doctorName", doctorName,
                "patientName", patientName,
                "doctorAddress", address,
                "appointmentTime", appointmentTime,
                "appointmentUrl", "https://medwell2.vercel.app/",
                "latitude", lat,
                "longitude", lon
        )
        );
        String htmlContent = engine.process("reminderTemplate", context);
        sendHtmlMail(email,subject,htmlContent);
    }

    @Async
    public void sendAppointmentConfirmationMail(String email,String subject,String doctorName,String patientName,String address,String appointmentTime,double lat,double lon) throws MessagingException {
        Context context = new Context();
        context.setVariables(Map.of(
                        "doctorName", doctorName,
                        "patientName", patientName,
                        "doctorAddress", address,
                        "appointmentTime", appointmentTime,
                        "appointmentDetailsUrl", "https://medwell2.vercel.app/",
                        "doctorLat", lat,
                        "doctorLon", lon
                )
        );
        String htmlContent = engine.process("appointmentBookingSuccess", context);
        sendHtmlMail(email,subject,htmlContent);
    }

}
