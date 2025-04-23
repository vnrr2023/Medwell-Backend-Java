package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.respdto.PatientVisibleDoctorProfileDto;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.entity.DoctorProfile;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.exceptions.DoctorServiceException;
import com.example.medwell.medwellbackend.service.AppointmentService;
import com.example.medwell.medwellbackend.service.DoctorAdressService;
import com.example.medwell.medwellbackend.service.DoctorProfileService;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor/data")
public class ProfileController {

    @Autowired
    private DoctorProfileService doctorProfileService;

    @Autowired
    private DoctorAdressService addressService;

    @Autowired
    private DoctorServiceService doctorService;

    @GetMapping("/profile")
    public ResponseEntity<?> showProfile(@RequestAttribute("user_id") Long userId){
        try {
            DoctorProfile profile=doctorProfileService.getDoctorProfile(userId);
            return ResponseEntity.status(200).body(profile);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile/{doctorId}")
    public ResponseEntity<?> showProfileToPatient(@PathVariable("doctorId") Long doctorId){
        try {
            DoctorProfile profile=doctorProfileService.getDoctorProfile(doctorId);
            PatientVisibleDoctorProfileDto profileDto=PatientVisibleDoctorProfileDto.builder().id(profile.getId())
                    .profilePic(profile.getProfilePic())
                    .profileQr(profile.getProfileQr())
                    .education(profile.getEducation())
                    .phoneNumber(profile.getPhoneNumber())
                    .speciality(profile.getSpeciality())
                    .userId(profile.getUser().getId())
                    .verified(profile.getVerified())
                    .name(profile.getName())
                    .build();

            return ResponseEntity.status(200).body(profileDto);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity<?> getDoctorAddresses(@RequestAttribute("user_id") Long userId){
        try {
            List<DoctorAddress> doctorAddressList =addressService.getDoctorAdresses(userId);
            return ResponseEntity.status(200).body(doctorAddressList);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/addresses/{doctorId}")
    public ResponseEntity<?> showDoctorAddressesToPatient(@PathVariable("doctorId") Long doctorId){
        try {
            List<DoctorAddress> doctorAddressList =addressService.getDoctorAdresses(doctorId);
            return ResponseEntity.status(200).body(doctorAddressList);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/services/{doctorId}")
    public ResponseEntity<?> showDoctorServicesToPatient(@PathVariable(value = "doctorId",required = true) Long doctorId){
        try {
            List<DoctorService> serviceList=doctorService.getDoctorServices(doctorId);
            return ResponseEntity.status(200).body(serviceList);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/services")
    public ResponseEntity<?> getDoctorServices(@RequestAttribute("user_id") Long userId){
        try {
            List<DoctorService> serviceList=doctorService.getDoctorServices(userId);
            return ResponseEntity.status(200).body(serviceList);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
