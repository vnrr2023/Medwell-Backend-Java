package com.example.medwell.medwellbackend.utility;



import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Properties;

@Component
@Getter
public class SecretsLoader {

    private final String emailHost,emailSenderAddress,emailPassword;
    private final String JWT_SECRET;
    private final int emailPort,redisPort;
    private final String redisHost,redisPassword;
    private final String groqApiKey;

    public SecretsLoader() throws  Exception{
        Properties props=new Properties();
        FileReader reader=new FileReader("src/main/resources/secrets.properties");
        props.load(reader);
        this.emailHost=props.getProperty("emailHost");
        this.emailPassword=props.getProperty("emailPassword");
        this.emailPort=Integer.parseInt(props.getProperty("emailPort"));
        this.emailSenderAddress=props.getProperty("emailUsername");
        this.JWT_SECRET=props.getProperty("JWT_SECRET");
        this.redisHost=props.getProperty("redisHost");
        this.redisPassword=props.getProperty("redisPassword");
        this.redisPort=Integer.parseInt(props.getProperty("redisPort"));
        this.groqApiKey=props.getProperty("groqApiKey");
    }

}
