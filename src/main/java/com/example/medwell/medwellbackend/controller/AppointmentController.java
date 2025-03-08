package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.AppointmentForPatientReqDto;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.service.AppointmentService;
import com.example.medwell.medwellbackend.service.DoctorAdressService;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorServiceService doctorService;

    @Autowired
    private DoctorAdressService adressService;

    @Autowired
    private DoctorServiceService doctorServiceService;

    @PostMapping("/create_appointment_from_patient")
    public ResponseEntity<?> createAppointmentFromPatient(@RequestBody AppointmentForPatientReqDto patientReqDto, @RequestAttribute("user_id") Long user_id) throws MessagingException {

        return appointmentService.createAppointmentPatient(user_id,patientReqDto.getService_id(),patientReqDto.getDoctor_id(),patientReqDto.getSlot_id());

    }

    @GetMapping("/get-doctor-addresses/{doctor_id}")
    public ResponseEntity<?> getDoctorAddresses(@PathVariable(value = "doctor_id",required = true) Long doctor_id){
        List<DoctorAddress> addressList=adressService.getDoctorAdresses(doctor_id);
        return ResponseEntity.status(200).body(addressList);
    }

    @GetMapping("/get-doctor-services/{doctor_id}")
    public ResponseEntity<?> getDoctorServices(@PathVariable(value = "doctor_id",required = true) Long doctor_id){
        List<DoctorService> serviceList=doctorServiceService.getDoctorServices(doctor_id);
        return ResponseEntity.status(200).body(serviceList);
    }






}
