package com.example.medwell.medwellbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_shiftedappointment")
public class ShiftedAppointment {

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "doctor_message")
    private String doctorMessage;


    @Column(name = "patient_accepted", nullable = false)
    private Boolean patientAccepted=false;

    @Column(name = "patient_message")
    private String patientMessage;

    @CreationTimestamp
    @Column(name = "event_at")
    private LocalDateTime eventAt;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @OneToOne
    @JoinColumn(name = "shifted_slot_id", nullable = false)
    private AppointmentSlot appointmentSlot;


    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }
}
