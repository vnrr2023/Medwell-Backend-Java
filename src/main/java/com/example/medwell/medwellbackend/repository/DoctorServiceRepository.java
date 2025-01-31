package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorServiceRepository extends JpaRepository<DoctorService, UUID>{

}