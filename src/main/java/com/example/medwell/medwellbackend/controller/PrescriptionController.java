package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.PrescriptionDto;
import com.example.medwell.medwellbackend.entity.DoctorPrescription;
import com.example.medwell.medwellbackend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/add")
    public ResponseEntity<?> addPrescription(@RequestBody PrescriptionDto prescriptionDto){

        prescriptionService.createPrescription(prescriptionDto.getPrescriptionData(),prescriptionDto.getOtherData(),prescriptionDto.getAppointmentId());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentPrescription(@PathVariable("appointmentId") String appointmentId){

        DoctorPrescription prescription=prescriptionService.getDoctorPrescription(appointmentId);
        return ResponseEntity.status(200).body(prescription);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePrescription(@RequestBody PrescriptionDto prescriptionDto){
        DoctorPrescription prescription=prescriptionService.updatePrescription(prescriptionDto);
        return ResponseEntity.status(201).body(prescription);
    }


}
