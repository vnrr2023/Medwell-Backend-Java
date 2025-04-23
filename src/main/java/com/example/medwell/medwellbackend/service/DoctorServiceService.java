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
import java.util.UUID;

@Service
public class DoctorServiceService {

    @Autowired
    private DoctorServiceRepository doctorServiceRepository;

    @Autowired
    private CustomUserRepository customUserRepository;


    public ResponseEntity<?> addService(DoctorServiceReqDto doctorServiceReqDto, Long userId) {
        CustomUser doctor= customUserRepository.getReferenceById(userId);
        DoctorService doctorService= DoctorService.builder().serviceName(doctorServiceReqDto.getServiceName())
                .serviceAmount(doctorServiceReqDto.getAmount()).doctor(doctor).build();
        doctorServiceRepository.save(doctorService);
        return ResponseEntity.status(201).body(Map.of("mssg","Service "+doctorServiceReqDto.getServiceName()+" added successfully"));
    }

    public List<DoctorService>  getDoctorServices(Long userId) {
        CustomUser doctor= customUserRepository.getReferenceById(userId);
        List<DoctorService> services = doctorServiceRepository.findByDoctorOrderByServiceAmountAsc(doctor);
        return services;

    }

    public ResponseEntity<?> updateDoctorService(DoctorServiceReqDto doctorServiceReqDto) {
        DoctorService service=doctorServiceRepository.getReferenceById(UUID.fromString(doctorServiceReqDto.getServiceId()));
        service.setServiceName(doctorServiceReqDto.getServiceName());
        service.setServiceAmount(doctorServiceReqDto.getAmount());
        doctorServiceRepository.save(service);
        return ResponseEntity.status(201).body(Map.of("mssg","updated service "+service.getServiceName()+" successfully","service",service));

    }

    public ResponseEntity<?> deleteServiceForDoctor(String serviceId) {
        DoctorService service=doctorServiceRepository.getReferenceById(UUID.fromString(serviceId));
        if (service==null){
            return ResponseEntity.status(400).body(Map.of("mssg","No such service found"));
        }
        String serviceName=service.getServiceName();
        doctorServiceRepository.delete(service);
        return ResponseEntity.status(200).body(Map.of("Mssg","Deleted service named "+serviceName));
    }
}
