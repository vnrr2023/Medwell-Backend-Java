package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.entity.*;
import com.example.medwell.medwellbackend.repository.AppointmentRepository;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import com.example.medwell.medwellbackend.scheduler.AppointmentEmailScheduler;
import com.example.medwell.medwellbackend.scheduler.AppointmentScheduler;
import com.example.medwell.medwellbackend.utility.MailUtility;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import java.util.Map;

@Service
public class AppointmentService {

    @Autowired
    private CustomUserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorServiceRepository doctorServiceRepository;

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private AppointmentEmailScheduler appointmentEmailScheduler;


    public ResponseEntity<?> createAppointmentPatient(Long userId, String serviceId, String doctorId, String slotId) throws MessagingException {
        CustomUser patient=userRepository.getReferenceById(userId);
        DoctorService service=doctorServiceRepository.getReferenceById(UUID.fromString(serviceId));
        CustomUser doctor=service.getDoctor();
        AppointmentSlot appointmentSlot=appointmentSlotRepository.getReferenceById(UUID.fromString(slotId));
        appointmentSlot.setStatus("BOOKED");
        appointmentSlotRepository.save(appointmentSlot);
        Appointment appointment=Appointment.builder()
                .bookedAt(LocalDateTime.now())
                .doctor(doctor)
                .patient(patient)
                .doctorServices(service)
                .appointmentSlot(appointmentSlot)
                .build();

        appointmentRepository.save(appointment);
        DoctorAddress doctorAddress=appointmentSlot.getDoctorAddress();
        LocalTime time = LocalTime.parse(appointmentSlot.getTiming());
        LocalDateTime localDateTime = LocalDateTime.of(appointmentSlot.getDate(), time);


        appointmentEmailScheduler.createRemindersAndSendConfirmationMail(patient.getEmail(),doctor.getFirstName(),patient.getFirstName(),doctorAddress.getAddress(),localDateTime,doctorAddress.getLat(),doctorAddress.getLon());


        return ResponseEntity.status(201).body(Map.of("mssg","Your Appointment has been booked Sucessfully!!"));

    }
}
