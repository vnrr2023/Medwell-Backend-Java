package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.Appointment;
import com.example.medwell.medwellbackend.entity.DoctorPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorPrescriptionRepository extends JpaRepository<DoctorPrescription, UUID> {

    DoctorPrescription findByAppointment(Appointment appointment);

}