package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.Doctorservices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DoctorServicesRepository extends JpaRepository<Doctorservices, UUID>{

}