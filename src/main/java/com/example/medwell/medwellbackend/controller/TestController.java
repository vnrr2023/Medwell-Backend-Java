package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import com.example.medwell.medwellbackend.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private DoctorServiceRepository doctorServiceRepository;

    @Autowired
    private JwtUtility jwtUtility;

    @GetMapping("/test")
    public ResponseEntity<?> testUser(@RequestHeader("Authorization") String token){
//        CustomUser user=customUserRepository.findById(148L).get();
//        DoctorService doctorService= DoctorService.builder().serviceName("regular checkup").serviceAmount("500").doctor(user).build();
//        doctorServiceRepository.save(doctorService);
        token=token.substring(7);
        System.out.println(jwtUtility.isValid(token));
        return ResponseEntity.status(200).build();
    }
}
