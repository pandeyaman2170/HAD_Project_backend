package com.example.teleconsultationbackend.Entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Schema(
        description = "Store the Consultation details1"
)
public class Consultation1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consultationId;

    @Column(name = "consultation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date consultationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient", referencedColumnName = "patient_id")
    private Patient patient;

    private Long depId;

    /*
        * Ongoing
        * accepted
        * Waiting
        * ended
     */
    private String status;
    public Consultation1(Date consultationDate, Doctor doctor, Patient patient, String status, Long depId) {
        this.consultationDate = consultationDate;
        this.doctor = doctor;
        this.patient = patient;
        this.status = status;
        this.depId = depId;
    }
}
