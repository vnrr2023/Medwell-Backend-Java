package com.example.medwell.medwellbackend.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    private String SECRET_KEY;

    public JwtUtility(SecretsLoader loader){
        this.SECRET_KEY=loader.getJWT_SECRET();
    }

    private Claims extractBody(String token){
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Map<String,String> isValid(String token){
        System.out.println(token);
        Map<String,String> authData=new HashMap<>();
        Claims body=extractBody(token);
        System.out.println("body="+body.getExpiration());
        if (body.getExpiration().after(new Date())){
            authData.put("status","true");
            authData.put("user_id", String.valueOf(body.get("user_id")));
        }
        authData.put("status","false");
        return authData;

    }


}
