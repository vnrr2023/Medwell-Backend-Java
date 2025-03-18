package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.entity.*;
import com.example.medwell.medwellbackend.repository.*;
import com.example.medwell.medwellbackend.scheduler.AppointmentEmailScheduler;
import com.example.medwell.medwellbackend.utility.MessagingUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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
import java.util.*;
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

    @Autowired
    private ShiftedAppointmentRepository shiftedAppointmentRepository;

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

    public ResponseEntity<?> createAppointmentPatient(Long userId, String serviceId, String doctorId, String slotId) throws MessagingException, JsonProcessingException {
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
                .status("BOOKED")
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

    @Transactional
    public boolean cancelAppointmentFromPatient(String appointmentId, String date, String time) {
        Appointment appointment=appointmentRepository.getReferenceById(UUID.fromString(appointmentId));
        AppointmentSlot appointmentSlot=appointment.getAppointmentSlot();
        LocalTime localTime=LocalTime.parse(appointmentSlot.getTiming());
        LocalDateTime dateTime=LocalDateTime.of(appointmentSlot.getDate(),localTime);
        if (LocalDateTime.now().isAfter(dateTime.minusHours(3))){
            return false;
        }
        appointmentSlot.setStatus("Available");
        appointmentSlotRepository.save(appointmentSlot);
        appointment.setStatus("Available");
        appointment.setPatient(null);
        appointment.setDoctor(null);
        appointment.setAppointmentSlot(null);
        appointment.setDoctorServices(null);
        appointmentRepository.save(appointment);
        return true;
    }


    public void shiftAppointment(String appointmentId, String shiftedSlotId, String message) throws JsonProcessingException {
        Appointment appointment=appointmentRepository.getReferenceById(UUID.fromString(appointmentId));
        AppointmentSlot shiftedSlot=appointmentSlotRepository.getReferenceById(UUID.fromString(shiftedSlotId));
        AppointmentSlot appointmentSlot=appointment.getAppointmentSlot();
        appointmentSlot.setStatus("SHIFTED");
        appointment.setStatus("SHIFTED");
        shiftedSlot.setStatus("PENDING");
        appointmentSlotRepository.save(shiftedSlot);
        appointmentSlotRepository.save(appointmentSlot);
        ShiftedAppointment shiftedAppointment=ShiftedAppointment.builder()
                .appointment(appointment)
                .appointmentSlot(shiftedSlot)
                .doctorMessage(message)
                .build();
        messagingUtility.sendMarkettingMessage(String.format("""
                Your appointment with *Doctor : %s* for *%s* has been shifted. 📅
                New Slot: %s at %s
                Previous Slot: %s at %s
                Reason: %s
                Please confirm your availability. ✅
                """,appointment.getDoctor().getFirstName(),appointment.getDoctorServices().getServiceName(),shiftedSlot.getDate(),shiftedSlot.getTiming(),appointmentSlot.getDate(),appointmentSlot.getTiming(),message),List.of("7506375933"));


        shiftedAppointmentRepository.save(shiftedAppointment);

    }

    public void acceptOrRejectShiftedAppointment(String action, String message, String shiftedAppointmentId){

        ShiftedAppointment shiftedAppointment=shiftedAppointmentRepository.getReferenceById(UUID.fromString(shiftedAppointmentId));
        Appointment appointment=shiftedAppointment.getAppointment();
        AppointmentSlot appointmentSlot=shiftedAppointment.getAppointmentSlot();
        if(action.equals("ACCEPT")){
            shiftedAppointment.setPatientAccepted(true);
            appointment.setStatus("BOOKED");
            appointmentSlot.setStatus("BOOKED");
            shiftedAppointment.setPatientMessage(message);
        }
        else{
            appointment.setStatus("REJECTED");
            appointment.setAppointmentSlot(null);
            appointment.setDoctorServices(null);
            appointmentSlot.setStatus("Available");
            shiftedAppointment.setPatientMessage(message);
        }
        appointmentSlotRepository.save(appointmentSlot);
        appointmentRepository.save(appointment);
        shiftedAppointmentRepository.save(shiftedAppointment);

    }

    public void markPatientStatus(String appointmentId,String status){
        Appointment appointment=appointmentRepository.getReferenceById(UUID.fromString(appointmentId));
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }




}


