package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorServiceMarketting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceMarkettingRepository extends JpaRepository<DoctorServiceMarketting, String> {

    List<DoctorServiceMarketting> findByDoctor(CustomUser doctor);
    int countByDoctor(CustomUser user);
}