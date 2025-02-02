package com.example.medwell.medwellbackend.config;


import com.example.medwell.medwellbackend.utility.SecretsLoader;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private String host,password;
    private int port;


    public RedisConfig(SecretsLoader loader) {
        this.host = loader.getRedisHost();
        this.password = loader.getRedisPassword();
        this.port = loader.getRedisPort();
    }

    @Bean
    public RedisConnectionFactory getConnFactory(){
        RedisStandaloneConfiguration config=new RedisStandaloneConfiguration();
        config.setPassword(this.password);
        config.setHostName(this.host);
        config.setPort(this.port);

        LettuceConnectionFactory factory=new LettuceConnectionFactory(config);
        factory.setValidateConnection(true);
        return  factory;

    }

    @Bean
    public RedisTemplate<String,Object> getTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


}
