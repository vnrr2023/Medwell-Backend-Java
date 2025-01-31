package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorDoctorservices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorDoctorservicesRepository extends JpaRepository<DoctorDoctorservices, String>, JpaSpecificationExecutor<DoctorDoctorservices> {

}