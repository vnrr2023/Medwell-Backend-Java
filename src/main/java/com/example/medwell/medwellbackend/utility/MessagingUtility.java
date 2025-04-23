package com.example.medwell.medwellbackend.utility;

import com.example.medwell.medwellbackend.dto.reqdto.AppointmentMessage;
import com.example.medwell.medwellbackend.dto.reqdto.MarkettingMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessagingUtility {

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public void sendAppointmentMessage(AppointmentMessage message) throws JsonProcessingException {

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonPayload = new ObjectMapper().writeValueAsString(message);
        HttpEntity<String> entity=new HttpEntity<>(jsonPayload,headers);
        String url="https://whatsapp-messaging-medwell-api.vercel.app/whatsapp/appointment-confirmation";
        ResponseEntity<Map> resp=restTemplate.postForEntity(url,entity, Map.class);

    }


    @Async
    public void sendMarkettingMessage(String message, List<String > phoneNumberList) throws JsonProcessingException {

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (String phoneNumber:phoneNumberList){
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("phone_number", phoneNumber);
            requestBody.put("message", message);

            String jsonPayload = new ObjectMapper().writeValueAsString(requestBody);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://whatsapp-messaging-medwell-api.vercel.app/whatsapp/marketing",
                    request,
                    String.class
            );

        }

    }


}
