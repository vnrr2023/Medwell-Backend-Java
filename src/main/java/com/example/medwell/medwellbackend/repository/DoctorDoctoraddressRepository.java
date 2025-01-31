package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorDoctoraddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorDoctoraddressRepository extends JpaRepository<DoctorDoctoraddress, Long>, JpaSpecificationExecutor<DoctorDoctoraddress> {

}