package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.Appointment;
import com.example.medwell.medwellbackend.entity.CustomUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{

    Page<Appointment> findByDoctorOrderByAppointmentSlot_DateDesc(CustomUser doctor,  Pageable pageable);

    Page<Appointment> findByPatientOrderByAppointmentSlot_DateDesc(CustomUser patient, Pageable pageable);

    List<Appointment> findByDoctorAndAppointmentSlot_StatusAndAppointmentSlot_Date(CustomUser doctor, String status, LocalDate date);

    List<Appointment> findByPatientAndAppointmentSlot_StatusAndAppointmentSlot_DateGreaterThanEqual(CustomUser patient, String status, LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE EXTRACT(MONTH FROM a.appointmentSlot.date) = :month AND EXTRACT(YEAR FROM a.appointmentSlot.date) = :year AND a.patient.id = :patientId")
    List<Appointment> findByMonthYearAndPatient(@Param("month") int month, @Param("year") int year, @Param("patientId") Long patientId);

    @Query("SELECT a FROM Appointment a WHERE EXTRACT(MONTH FROM a.appointmentSlot.date) = :month AND EXTRACT(YEAR FROM a.appointmentSlot.date) = :year AND a.doctor.id = :doctorId")
    List<Appointment> findByMonthYearAndDoctor(@Param("month") int month, @Param("year") int year, @Param("doctorId") Long doctorId);


}