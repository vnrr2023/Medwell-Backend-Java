package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {

}