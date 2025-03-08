package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.DoctorServiceReqDto;
import com.example.medwell.medwellbackend.dto.respdto.ErrorResponse;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.exceptions.DoctorServiceException;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> addService(@RequestBody DoctorServiceReqDto doctorServiceReqDto, HttpServletRequest request){
        String userId= (String) request.getAttribute("user_id");
        return doctorServiceService.addService(doctorServiceReqDto,userId);
    }

    @GetMapping("/get_services")
    public ResponseEntity<?> getServices(@RequestAttribute("user_id") Long user_id){
//        String userId= (String) request.getAttribute("user_id");
        List<DoctorService> doctorServices= doctorServiceService.getDoctorServices(user_id);
        return ResponseEntity.status(200).body(Map.of("services",doctorServices));
    }

    @PostMapping("/update_service")
    public ResponseEntity<?> updateService(@RequestBody DoctorServiceReqDto doctorServiceReqDto){
        System.out.println(doctorServiceReqDto);
        return doctorServiceService.updateDoctorService(doctorServiceReqDto);

    }

    @PostMapping("/delete_service")
    public   ResponseEntity<?> deleteDoctorService(@RequestBody DoctorServiceReqDto doctorServiceReqDto){
        return doctorServiceService.deleteServiceForDoctor(doctorServiceReqDto.getServiceId());
    }

    @ExceptionHandler(DoctorServiceException.class)
    public ResponseEntity<?> handleException(DoctorServiceException exception){
        ErrorResponse response=new ErrorResponse(exception.getMssg(),exception.getStatus(),new Date());
        return new ResponseEntity<>(response,exception.getStatus());
    }

}
