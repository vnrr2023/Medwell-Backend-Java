package com.example.medwell.medwellbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_appointmentpayment")
public class AppointmentPayment {

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }

}
