package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.DoctorServiceReqDto;
import com.example.medwell.medwellbackend.dto.respdto.DoctorServiceResDto;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorService;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceService {

    @Autowired
    private DoctorServiceRepository doctorServiceRepository;

    @Autowired
    private CustomUserRepository customUserRepository;


    public ResponseEntity<?> addService(DoctorServiceReqDto doctorServiceReqDto, String userId) {
        CustomUser doctor= customUserRepository.getReferenceById(Long.parseLong(userId));
        DoctorService doctorService= DoctorService.builder().serviceName(doctorServiceReqDto.getServiceName())
                .serviceAmount(doctorServiceReqDto.getAmount()).doctor(doctor).build();
        doctorServiceRepository.save(doctorService);
        return ResponseEntity.status(201).body(Map.of("mssg","Service "+doctorServiceReqDto.getServiceName()+" added successfully"));
    }

    public ResponseEntity<?> getDoctorServices(String userId) {
        CustomUser doctor= customUserRepository.getReferenceById(Long.parseLong(userId));
        List<DoctorService> services = doctorServiceRepository.findByDoctorOrderByServiceAmountAsc(doctor);
        return ResponseEntity.status(200).body(Map.of("services",services));

    }
}
