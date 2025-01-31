package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorAddress;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAddressRepository extends JpaRepository<DoctorAddress, Long> {

}