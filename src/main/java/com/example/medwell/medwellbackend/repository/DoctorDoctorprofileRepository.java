package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorDoctorprofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorDoctorprofileRepository extends JpaRepository<DoctorDoctorprofile, String>, JpaSpecificationExecutor<DoctorDoctorprofile> {

}