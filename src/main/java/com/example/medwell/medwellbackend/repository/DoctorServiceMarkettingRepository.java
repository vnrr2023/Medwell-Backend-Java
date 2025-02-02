package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorServiceMarketting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorServiceMarkettingRepository extends JpaRepository<DoctorServiceMarketting, String> {

}