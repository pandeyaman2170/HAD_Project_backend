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
public class FollowUpDetails {
    @Temporal(TemporalType.DATE)
    private Date followUpDate;

    private String departmentName;

    private String doctorName;

    private String observation;

    private  boolean doctor_status;

    private String phone_number;
}
