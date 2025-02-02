package com.example.medwell.medwellbackend.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor_servicemarketting")
public class DoctorServiceMarketting {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "html")
    private String html;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private CustomUser doctor;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id= NanoIdUtils.randomNanoId();
        }
    }

}
