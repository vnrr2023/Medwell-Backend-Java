package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.DoctorAppointmentslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorAppointmentslotRepository extends JpaRepository<DoctorAppointmentslot, String>, JpaSpecificationExecutor<DoctorAppointmentslot> {

}