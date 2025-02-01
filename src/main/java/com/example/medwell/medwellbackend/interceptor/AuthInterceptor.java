package com.example.medwell.medwellbackend.interceptor;

import com.example.medwell.medwellbackend.utility.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtility jwtUtility;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token= request.getHeader("Authorization");
        if (token==null || !token.startsWith("Bearer ")){
            PrintWriter writer=response.getWriter();
            writer.write("{\"status\": 401, \"errorMssg\": \"Token is null or doesn't start with Bearer\"}");
            response.setStatus(401);
            return false;
        }
        token=token.substring(7);
        Map<String,String> authData=jwtUtility.isValid(token);
        if (Boolean.parseBoolean(authData.get("status"))){
            request.setAttribute("user_id",authData.get("user_id"));
            return true;
        }
        PrintWriter writer=response.getWriter();
        writer.write("{\"status\": 401, \"errorMssg\": \"Token is not valid!!\"}");
        response.setStatus(401);
        return false;
    }
}
