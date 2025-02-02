package com.example.medwell.medwellbackend.dto.reqdto;

import lombok.Data;

import java.util.List;

@Data
public class MarketToCustomersReqDto {
    private String html,subject;
    private List<String> emails;
}
