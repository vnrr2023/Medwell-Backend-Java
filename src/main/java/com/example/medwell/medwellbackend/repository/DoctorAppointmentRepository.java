package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, String>, JpaSpecificationExecutor<DoctorAppointment> {

}