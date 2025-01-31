package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID>{

}