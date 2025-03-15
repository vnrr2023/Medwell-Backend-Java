package com.example.medwell.medwellbackend.dto.reqdto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkettingMessage {

    private String message;
    private String phone_number;

}
