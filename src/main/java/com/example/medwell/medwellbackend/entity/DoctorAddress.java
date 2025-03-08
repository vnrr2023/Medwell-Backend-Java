package com.example.medwell.medwellbackend.entity;

import com.example.medwell.medwellbackend.serializer.CustomUserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_doctoraddress")
public class DoctorAddress {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "address",length = 1000,columnDefinition = "text")
    private String address;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonSerialize(using = CustomUserSerializer.class)
    private CustomUser doctor;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "timings",columnDefinition = "jsonb")
    private Map<String,String> timings;

}
