package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.AppointmentForPatientReqDto;
import com.example.medwell.medwellbackend.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create_appointment_from_patient")
    public ResponseEntity<?> createAppointmentFromPatient(@RequestBody AppointmentForPatientReqDto patientReqDto, HttpServletRequest request){
        Long user_id=Long.parseLong((String) request.getAttribute("user_id"));
        return appointmentService.createAppointmentPatient(user_id,patientReqDto.getService_id(),patientReqDto.getDoctor_id(),patientReqDto.getSlot_id());

    }



}
