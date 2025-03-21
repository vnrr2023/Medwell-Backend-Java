package com.example.medwell.medwellbackend.entity;

import com.example.medwell.medwellbackend.serializer.CustomUserSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_appointment")
public class Appointment {

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @CreationTimestamp
    @Column(name = "booked_at", updatable = false)
    private LocalDateTime bookedAt;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "appointment_slot_id", nullable = true)
    private AppointmentSlot appointmentSlot;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    @JsonSerialize(using = CustomUserSerializer.class)
    private CustomUser doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    @JsonSerialize(using = CustomUserSerializer.class)
    private CustomUser patient;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = true)
    private DoctorService doctorServices;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }
}
