package com.example.medwell.medwellbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_doctorservices")
public class Doctorservices {


    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_amount")
    private String serviceAmount;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private CustomUser doctor;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }

}
