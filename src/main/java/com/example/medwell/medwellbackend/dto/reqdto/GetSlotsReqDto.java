package com.example.medwell.medwellbackend.dto.reqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetSlotsReqDto {
    private String date;
    @JsonProperty("address_id")
    private String addressId;
}
