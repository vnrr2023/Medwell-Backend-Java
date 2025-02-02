package com.example.medwell.medwellbackend.utility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtility {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void setString(String key,String value){
        redisTemplate.opsForValue().set(key,value,30, TimeUnit.MINUTES);
    }

    public String getString(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setObject(String key,Object obj){
        redisTemplate.opsForValue().set(key,obj);
    }

    public Object getObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
