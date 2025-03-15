package com.example.medwell.medwellbackend.dto.reqdto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentMessage {
    private String patientName;
    private String doctorName;
    private String address;
    private String datetime;
    private String detailsLink;
    private String directionLink;
    private String mobileNumber;
}
