package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.AppointmentForPatientReqDto;
import com.example.medwell.medwellbackend.entity.Appointment;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.service.AppointmentService;
import com.example.medwell.medwellbackend.service.DoctorAdressService;
import com.example.medwell.medwellbackend.service.DoctorServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/patient/create")
    public ResponseEntity<?> createAppointmentFromPatient(@RequestBody AppointmentForPatientReqDto patientReqDto, @RequestAttribute("user_id") Long user_id) throws MessagingException, JsonProcessingException {

        return appointmentService.createAppointmentPatient(user_id,patientReqDto.getService_id(),patientReqDto.getDoctor_id(),patientReqDto.getSlot_id());

    }

    @GetMapping("/doctor/previous-appointments")
    public ResponseEntity<?> getPreviousAppointmentsOfDoctor(@RequestAttribute("user_id") long user_id,
                                                     @RequestParam(value = "page",defaultValue = "0",required = false) int page
                                                     ){
        int pageSize=5;
        Page<Appointment> appointmentPage=appointmentService.getPreviousAppointments(user_id,true,page,pageSize);
        Map<String, Object> response = new HashMap<>();
        response.put("appointments", appointmentPage.getContent());
        response.put("currentPage", appointmentPage.getNumber());
        response.put("totalPages", appointmentPage.getTotalPages());
        response.put("hasNext", appointmentPage.hasNext());
        response.put("nextPage", appointmentPage.getNumber()+1);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/patient/previous-appointments")
    public ResponseEntity<?> getPreviousAppointmentsOfPatients(@RequestAttribute("user_id") long user_id,
                                                     @RequestParam(value = "page",defaultValue = "0",required = false) int page
    ){
        int pageSize=5;
        Page<Appointment> appointmentPage=appointmentService.getPreviousAppointments(user_id,false,page,pageSize);
        Map<String, Object> response = new HashMap<>();
        response.put("appointments", appointmentPage.getContent());
        response.put("currentPage", appointmentPage.getNumber());
        response.put("totalPages", appointmentPage.getTotalPages());
        response.put("hasNext", appointmentPage.hasNext());
        response.put("nextPage", appointmentPage.getNumber()+1);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/doctor/upcoming-appointments")
    public ResponseEntity<?> getUpcomingAppointmentsForDoctor(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "time",required = false) String time,
            @RequestAttribute("user_id") long doctorId
    ){

        return ResponseEntity.status(200).body(appointmentService.getUpcomingAppointements(121,true,date,time));

    }

    @GetMapping("/patient/upcoming-appointments")
    public ResponseEntity<?> getUpcomingAppointmentsForPatient(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "time",required = false) String time,
            @RequestAttribute("user_id") long doctorId
    ){

        return ResponseEntity.status(200).body(appointmentService.getUpcomingAppointements(121,false,date,time));

    }

    @GetMapping("/patient/appointment-calendar")
    public ResponseEntity<?> getCalendarForPatient(
            @RequestParam("year") int year,@RequestParam("month") int month,@RequestAttribute("user_id") long userId
    ){
        List<Appointment> appointmentList=appointmentService.getAppointmentCalendar(userId,false,month,year);
        return ResponseEntity.status(200).body(appointmentList);
    }


    @GetMapping("/doctor/appointment-calendar")
    public ResponseEntity<?> getCalendarForDoctor(
            @RequestParam("year") int year,@RequestParam("month") int month,@RequestAttribute("user_id") long userId
    ){
        List<Appointment> appointmentList=appointmentService.getAppointmentCalendar(userId,true,month,year);
        return ResponseEntity.status(200).body(appointmentList);
    }


    @PostMapping("/patient/cancel")
    public ResponseEntity<?> cancelAppointmentFromPatient(@RequestBody Map<String,String> data){
        boolean status=appointmentService.cancelAppointmentFromPatient(data.get("appointmentId"),data.get("date"),data.get("time"));
        if (!status) return ResponseEntity.status(400).body(Map.of("message","Cancellation only allowed before 3 hours of appointment time"));
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/doctor/shift")
    public ResponseEntity<?> shiftAppointment(@RequestBody Map<String,String> data) throws JsonProcessingException {
        appointmentService.shiftAppointment(data.get("appointmentId"),data.get("shiftedSlotId"),data.get("message"));
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/patient/action")
    public ResponseEntity<?> performActiononAppointShift(@RequestBody Map<String,String> data){
        appointmentService.acceptOrRejectShiftedAppointment(data.get("action"),data.get("message"),data.get("shiftedAppointmentId"));
        return ResponseEntity.status(200).build();
    }






}
