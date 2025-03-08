package com.example.medwell.medwellbackend.service;


import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorAdressService {

    @Autowired
    private DoctorAddressRepository addressRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    public List<DoctorAddress> getDoctorAdresses(Long doctor_id){
        CustomUser doctor=customUserRepository.getReferenceById(doctor_id);
        List<DoctorAddress> addressList=addressRepository.findByDoctor(doctor);
        return addressList;
    }

}
