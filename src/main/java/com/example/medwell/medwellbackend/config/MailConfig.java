package com.example.medwell.medwellbackend.config;

import com.example.medwell.medwellbackend.utility.SecretsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {


    private SecretsLoader loader;

    public MailConfig(SecretsLoader loader) {
        this.loader = loader;
    }

    @Bean
    public JavaMailSender configureMailSender(){
        JavaMailSenderImpl sender=new JavaMailSenderImpl();
        sender.setPort(loader.getEmailPort());
        sender.setUsername(loader.getEmailSenderAddress());
        sender.setPassword(loader.getEmailPassword());
        sender.setHost(loader.getEmailHost());
        Properties properties=sender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        return sender;
    }
}
