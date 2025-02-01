package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.DoctorServiceReqDto;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor_service")
public class DoctorServiceController {

    @Autowired
    private DoctorServiceService doctorServiceService;

    @PostMapping("/add_service")
    public ResponseEntity<?> addService(@RequestBody DoctorServiceReqDto doctorServiceReqDto, HttpServletRequest request){
        String userId= (String) request.getAttribute("user_id");
        return doctorServiceService.addService(doctorServiceReqDto,userId);
    }

    @GetMapping("/get_services")
    public ResponseEntity<?> getServices(HttpServletRequest request){
        String userId= (String) request.getAttribute("user_id");
        return doctorServiceService.getDoctorServices(userId);
    }

}
