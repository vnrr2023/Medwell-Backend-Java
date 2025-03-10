package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorProfile;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorProfileService {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    public DoctorProfile getDoctorProfile(Long doctor_id) {
        CustomUser doctor = customUserRepository.getReferenceById(doctor_id);
        return doctorProfileRepository.findByUser(doctor);

    }
}
