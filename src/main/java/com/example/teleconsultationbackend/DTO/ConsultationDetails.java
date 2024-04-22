package com.example.teleconsultationbackend.DTO;

import com.example.teleconsultationbackend.Entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDetails {
    private int consultationId;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private String string;
    private String status;
}
