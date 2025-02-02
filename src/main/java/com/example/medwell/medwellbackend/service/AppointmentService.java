package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.entity.Appointment;
import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.repository.AppointmentRepository;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ResponseEntity<?> createAppointmentPatient(Long userId, String serviceId, String doctorId, String slotId) {
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
        return ResponseEntity.status(201).body(Map.of("mssg","Your Appointment has been booked Sucessfully!!"));

    }
}
