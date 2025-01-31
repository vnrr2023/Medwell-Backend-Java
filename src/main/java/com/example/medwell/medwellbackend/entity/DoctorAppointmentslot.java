package com.example.medwell.medwellbackend.entity;

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
public class DoctorAppointmentslot {

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "timing")
    private String timing;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private AuthenticationCustomuser doctor;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private DoctorDoctoraddress doctoraddress;

}
