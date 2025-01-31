package com.example.medwell.medwellbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_doctorprofile")
public class DoctorDoctorprofile{

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false,length = 12)
    private String phoneNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "profile_qr")
    private String profileQr;

    @Column(name = "registeration_number")
    private String registerationNumber;

    @Column(name = "adhaar_card")
    private String adhaarCard;

    @Column(name = "registeration_card_image")
    private String registerationCardImage;

    @Column(name = "passport_size_image")
    private String passportSizeImage;

    @Column(name = "profile_pic", nullable = false)
    private String profilePic;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AuthenticationCustomuser user;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "education")
    private String education;

    @PrePersist
    public void assignId(){
        if (this.id==null){
            this.id=UUID.randomUUID();
        }
    }

}
