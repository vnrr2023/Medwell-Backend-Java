package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.UUID;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {

    DoctorProfile findByUser(CustomUser doctor);

}