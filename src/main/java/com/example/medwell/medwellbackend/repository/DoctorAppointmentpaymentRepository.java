package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorAppointmentpayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorAppointmentpaymentRepository extends JpaRepository<DoctorAppointmentpayment, String>, JpaSpecificationExecutor<DoctorAppointmentpayment> {

}