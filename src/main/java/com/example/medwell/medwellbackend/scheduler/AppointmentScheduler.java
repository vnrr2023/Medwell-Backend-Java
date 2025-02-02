package com.example.medwell.medwellbackend.scheduler;


import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.entity.DoctorProfile;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorAddressRepository;
import com.example.medwell.medwellbackend.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Scheduled(cron = "0 10 16 * * SUN")
    public void createAppointmentSlots() {
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        for (DoctorProfile profile : doctorProfileList) {
            CustomUser doctor = profile.getUser();
            List<DoctorAddress> doctorAddressList = doctorAddressRepository.findByDoctor(doctor);
            for (DoctorAddress doctorAddress : doctorAddressList) {
                Map<String, String> timing = doctorAddress.getTimings();
                if (timing == null) continue;
                LocalTime start = LocalTime.of(Integer.parseInt(timing.get("start").split(":")[0]), 0);
                LocalTime end = LocalTime.of(Integer.parseInt(timing.get("end").split(":")[0]), 0);

                LocalDate today = LocalDate.now();
                LocalDate nextMonday = today.with(java.time.DayOfWeek.MONDAY).plusWeeks(1);
                LocalDate nextSaturday = nextMonday.plusDays(5);
                for (LocalDate date = nextMonday; !date.isAfter(nextSaturday); date = date.plusDays(1)) {
                    generateSlotForDay(start, end,date,doctor,doctorAddress);
                }

            }

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
