package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private DoctorServiceRepository doctorServiceRepository;

    @GetMapping("/test")
    public ResponseEntity<?> testUser(){
        CustomUser user=customUserRepository.findById(148L).get();
        DoctorService doctorService= DoctorService.builder().serviceName("regular checkup").serviceAmount("500").doctor(user).build();
        doctorServiceRepository.save(doctorService);
        return ResponseEntity.status(200).body(doctorService);
    }
}
