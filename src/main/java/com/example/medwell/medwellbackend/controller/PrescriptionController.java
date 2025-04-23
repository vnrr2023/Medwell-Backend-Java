package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.PrescriptionDto;
import com.example.medwell.medwellbackend.entity.DoctorPrescription;
import com.example.medwell.medwellbackend.exceptions.DoctorServiceException;
import com.example.medwell.medwellbackend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/add")
    public ResponseEntity<?> addPrescription(@RequestBody PrescriptionDto prescriptionDto){

        try {
            prescriptionService.createPrescription(prescriptionDto.getPrescriptionData(),prescriptionDto.getOtherData(),prescriptionDto.getAppointmentId());

            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentPrescription(@PathVariable("appointmentId") String appointmentId){

        try {
            DoctorPrescription prescription=prescriptionService.getDoctorPrescription(appointmentId);
            return ResponseEntity.status(200).body(prescription);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePrescription(@RequestBody PrescriptionDto prescriptionDto){
        try {
            DoctorPrescription prescription=prescriptionService.updatePrescription(prescriptionDto);
            return ResponseEntity.status(201).body(prescription);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
