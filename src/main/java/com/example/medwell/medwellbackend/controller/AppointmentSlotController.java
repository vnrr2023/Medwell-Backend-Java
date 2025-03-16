package com.example.medwell.medwellbackend.controller;

import com.example.medwell.medwellbackend.dto.reqdto.GetSlotsReqDto;
import com.example.medwell.medwellbackend.service.AppointmentSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/slots")
public class AppointmentSlotController {

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @GetMapping("/get-slots-for-date-and-address/{date}/{addressId}")
    public ResponseEntity<?> getSlotsForDate(@PathVariable("date") String date,@PathVariable("addressId") Long addressId){
        return appointmentSlotService.showSlots(date,addressId);
    }


}
