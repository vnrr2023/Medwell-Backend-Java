package com.example.medwell.medwellbackend.controller;

import com.example.medwell.medwellbackend.dto.reqdto.GetSlotsReqDto;
import com.example.medwell.medwellbackend.service.AppointmentSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slots")
public class AppointmentSlotController {

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @PostMapping("/get_slots_for_date")
    public ResponseEntity<?> getSlotsForDate(@RequestBody GetSlotsReqDto getSlotsReqDto){
        return appointmentSlotService.showSlots(getSlotsReqDto);
    }




}
