package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.DoctorServiceReqDto;
import com.example.medwell.medwellbackend.dto.respdto.ErrorResponse;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.exceptions.DoctorServiceException;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor_service")
public class DoctorServiceController {

    @Autowired
    private DoctorServiceService doctorServiceService;

    @PostMapping("/add_service")
    public ResponseEntity<?> addService(@RequestBody DoctorServiceReqDto doctorServiceReqDto,@RequestAttribute("user_id") Long user_id){
        try {
            return doctorServiceService.addService(doctorServiceReqDto,user_id);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_services")
    public ResponseEntity<?> getServices(@RequestAttribute("user_id") Long user_id){
        try {
            List<DoctorService> doctorServices= doctorServiceService.getDoctorServices(user_id);
            return ResponseEntity.status(200).body(Map.of("services",doctorServices));
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_service")
    public ResponseEntity<?> updateService(@RequestBody DoctorServiceReqDto doctorServiceReqDto){
        try {
            return doctorServiceService.updateDoctorService(doctorServiceReqDto);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/delete_service")
    public   ResponseEntity<?> deleteDoctorService(@RequestBody DoctorServiceReqDto doctorServiceReqDto){
        try {
            return doctorServiceService.deleteServiceForDoctor(doctorServiceReqDto.getServiceId());
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
