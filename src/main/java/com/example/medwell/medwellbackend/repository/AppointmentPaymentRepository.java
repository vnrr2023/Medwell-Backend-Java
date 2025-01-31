package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.AppointmentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentPaymentRepository extends JpaRepository<AppointmentPayment, UUID> {

}