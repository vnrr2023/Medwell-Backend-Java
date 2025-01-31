package com.example.medwell.medwellbackend.repository;

import com.example.medwell.medwellbackend.entity.AuthenticationCustomuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthenticationCustomuserRepository extends JpaRepository<AuthenticationCustomuser, Long>{


}