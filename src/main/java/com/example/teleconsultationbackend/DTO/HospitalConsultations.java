package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalConsultations {
    private int prescriptionId;

    @Temporal(TemporalType.DATE)
    private Date consultationDate;

    private String observation;

    private Set<String> medicine;

    private String remark;

    private String doctorName;

    private long doctorId;

    private String patientName;

    private long patientId;
//
//    private String doctorName;

//    private String patientName;
}
