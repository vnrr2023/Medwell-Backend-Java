package com.example.medwell.medwellbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_appointmentslot")
public class AppointmentSlot {

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "timing")
    private String timing;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private CustomUser doctor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id")
    private DoctorAddress doctorAddress;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }

}
