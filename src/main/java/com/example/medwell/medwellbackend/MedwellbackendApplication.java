package com.example.medwell.medwellbackend;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class MedwellbackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(MedwellbackendApplication.class, args);

	}

}
