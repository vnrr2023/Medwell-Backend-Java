package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.GetSlotsReqDto;
import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.repository.AppointmentSlotRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentSlotService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private DoctorAddressRepository doctorAddressRepository;

    public ResponseEntity<?> showSlots(String date,Long addressId) {

        DoctorAddress address=doctorAddressRepository.getReferenceById(addressId);
        List<AppointmentSlot> appointmentSlotList=appointmentSlotRepository
                .findByDoctorAddressAndDateOrderByTimingAsc(address, LocalDate.parse(date));
        return ResponseEntity.status(200).body(appointmentSlotList);

    }
}
