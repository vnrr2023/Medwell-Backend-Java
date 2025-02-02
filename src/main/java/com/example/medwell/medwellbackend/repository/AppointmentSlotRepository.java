package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.AppointmentSlot;
import com.example.medwell.medwellbackend.entity.DoctorAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID>{

    List<AppointmentSlot> findByDoctorAddressAndDateOrderByTimingAsc(DoctorAddress doctorAddress, LocalDate date);

}