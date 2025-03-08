package com.example.medwell.medwellbackend.service;


import com.example.medwell.medwellbackend.utility.SecretsLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroqUtility {

    private final String API_KEY;
    private Map<String,String> systemRole;
    private final String model="llama-3.3-70b-versatile";

    @Autowired
    public GroqUtility(SecretsLoader loader){
        this.API_KEY=loader.getGroqApiKey();
        systemRole=new ConcurrentHashMap<>();
        systemRole.put("role","system");
        systemRole.put("content", """
               You are a Mail body builder. You will write a attractive and short body of around 4 to 5 sentences for a mail based on the subject given to you. This mail will be sent to the patients by the doctor to market services provided by the doctor for customer retention and customer relationship. 
               Just give the body without any preamble or extra text.
                """);
    }

    public ResponseEntity<?> chatWithGroq(String subject) throws Exception {

        Map<String,Object> requestBody=new HashMap<>();
        requestBody.put("model",model);
        Map<String,String> userRole=new ConcurrentHashMap<>();
        userRole.put("role","user");
        userRole.put("content", "<Subject>"+"Regarding starting X ray service at MayoClinic"+"</Subject>");

        requestBody.put("messages", List.of(systemRole,userRole));
        ObjectMapper mapper=new ObjectMapper();
        String jsonPayload= mapper.writeValueAsString(requestBody);
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse and print response
        Map<String, Object> jsonResponse = mapper.readValue(response.body(), Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonResponse.get("choices");
        Map<String, String> message = (Map<String, String>) choices.get(0).get("message");
        return ResponseEntity.status(201).body(
                Map.of("body",message.get("content")
                ));


    }


}
