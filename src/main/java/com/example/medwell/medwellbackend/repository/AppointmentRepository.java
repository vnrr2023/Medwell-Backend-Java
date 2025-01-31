package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{

}