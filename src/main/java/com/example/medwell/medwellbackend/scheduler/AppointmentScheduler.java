package com.example.medwell.medwellbackend.scheduler;


import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.entity.DoctorProfile;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorAddressRepository;
import com.example.medwell.medwellbackend.repository.DoctorProfileRepository;
import com.example.medwell.medwellbackend.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AppointmentScheduler {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private DoctorAddressRepository doctorAddressRepository;

    @Autowired
    private MailUtility mailUtility;


    @Scheduled(cron = "0 0 23 ? * WED")
    public void createAppointmentSlots() throws Exception {
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(java.time.DayOfWeek.MONDAY).plusWeeks(1);
        LocalDate nextSaturday = nextMonday.plusDays(5);

        for (DoctorProfile profile : doctorProfileList) {
            CustomUser doctor = profile.getUser();
            List<DoctorAddress> doctorAddressList = doctorAddressRepository.findByDoctor(doctor);
            boolean created=false;
            for (DoctorAddress doctorAddress : doctorAddressList) {
                Map<String, String> timing = doctorAddress.getTimings();
                if (timing == null) continue;
                created=true;
                LocalTime start = LocalTime.of(Integer.parseInt(timing.get("start").split(":")[0]), 0);
                LocalTime end = LocalTime.of(Integer.parseInt(timing.get("end").split(":")[0]), 0);


                for (LocalDate date = nextMonday; !date.isAfter(nextSaturday); date = date.plusDays(1)) {
                    generateSlotForDay(start, end,date,doctor,doctorAddress);
                }

            }
//            if (created) {
//                mailUtility.sendAppointmentSlotCreationMail(doctor.getEmail(), Map.of("doctorName", doctor.getFirstName() + doctor.getLastName(), "fromDate", nextMonday.format(formatter),"toDate",nextSaturday.format(formatter)));
//            }

        }
    }

    private void generateSlotForDay(LocalTime start,LocalTime end,LocalDate date,CustomUser doctor,DoctorAddress doctorAddress){
        while (start.isBefore(end)) {
            AppointmentSlot appointmentSlot= AppointmentSlot.builder()
                        .date(date)
                    .status("Available")
                    .timing(start.toString())
                    .doctor(doctor)
                    .doctorAddress(doctorAddress)
                    .build();
            appointmentSlotRepository.save(appointmentSlot);
            start=start.plusMinutes(30l);
        }

    }






}
