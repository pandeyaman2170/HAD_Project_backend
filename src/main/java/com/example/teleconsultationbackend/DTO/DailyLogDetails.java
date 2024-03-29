package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyLogDetails {

    private long doctorId;

    @Temporal(TemporalType.DATE)
    private Date currentDate;

    private long patientId;

    private String observation;

    private String remark;
}
