package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorAddressRepository extends JpaRepository<DoctorAddress, Long> {

}