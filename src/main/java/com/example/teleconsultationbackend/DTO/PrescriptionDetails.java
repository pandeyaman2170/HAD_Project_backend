package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrescriptionDetails {
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

    @Temporal(TemporalType.DATE)
    private Date followUpDate;

    private String hospital_name;
    private String department_name;



}

