package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long>{


}