package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.AppointmentMessage;
import com.example.medwell.medwellbackend.entity.*;
import com.example.medwell.medwellbackend.repository.AppointmentRepository;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import com.example.medwell.medwellbackend.scheduler.AppointmentEmailScheduler;
import com.example.medwell.medwellbackend.utility.MessagingUtility;
import jakarta.mail.MessagingException;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private MessagingUtility messagingUtility;

    private static final DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("HH:mm");

    private List<Appointment> filterByTime(List<Appointment> appointmentList,LocalTime givenTime){
        return appointmentList.stream()
                .filter(appointment -> {
                            try {
                                LocalTime appointmentTime = LocalTime.parse(appointment.getAppointmentSlot().getTiming(), TIME_FORMATTER);
                                return !appointmentTime.isBefore(givenTime); // Only keep times >= givenTime
                            } catch (Exception e) {
                                return false;
                            }
                        }
                )
                .sorted(Comparator.comparing(a -> LocalTime.parse(a.getAppointmentSlot().getTiming(), TIME_FORMATTER)))
                .collect(Collectors.toList());
    }

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

    public Page<Appointment> getPreviousAppointments(long userId, boolean isDoctor,int pageNumber,int pageSize) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        CustomUser user= userRepository.getReferenceById(userId);
        if (isDoctor) return appointmentRepository.findByDoctorOrderByAppointmentSlot_DateDesc(user,pageable);
        else return appointmentRepository.findByPatientOrderByAppointmentSlot_DateDesc(user,pageable);

    }

    public List<Appointment> getUpcomingAppointements(long userId, boolean isDoctor, LocalDate date, String time) {

        CustomUser user=userRepository.getReferenceById(userId);
        if (isDoctor) {
            List<Appointment> appointmentList = appointmentRepository.findByDoctorAndAppointmentSlot_StatusAndAppointmentSlot_Date(user, "BOOKED", date);
            if (time==null) return appointmentList;
            LocalTime givenTime = LocalTime.parse(time, TIME_FORMATTER);
            return filterByTime(appointmentList, givenTime);
        }
        else{
            List<Appointment> appointmentList = appointmentRepository.findByPatientAndAppointmentSlot_StatusAndAppointmentSlot_DateGreaterThanEqual(user, "BOOKED", date);
            if (time==null) return appointmentList;
            LocalTime givenTime = LocalTime.parse(time, TIME_FORMATTER);
            return filterByTime(appointmentList, givenTime);
        }
    }


    public List<Appointment> getAppointmentCalendar(long userId, boolean isDoctor, int month, int year) {
        if (isDoctor) return appointmentRepository.findByMonthYearAndDoctor(month,year,userId);
        return appointmentRepository.findByMonthYearAndPatient(month,year,userId);
    }
}
