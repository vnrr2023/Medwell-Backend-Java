package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorAddressRepository extends JpaRepository<DoctorAddress, Long> {
    List<DoctorAddress> findByDoctor(CustomUser doctor);

}