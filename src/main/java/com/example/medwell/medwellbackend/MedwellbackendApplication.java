package com.example.medwell.medwellbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@RestController
public class MedwellbackendApplication {


	@GetMapping("/health")
	public ResponseEntity<?> getHealth(){
		return ResponseEntity.ok(Map.of("message","Server is up and running"));
	}

	public static void main(String[] args) {

		SpringApplication.run(MedwellbackendApplication.class, args);

	}

}
