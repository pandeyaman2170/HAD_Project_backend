package com.example.teleconsultationbackend.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(
        description = "Store the Prescrption of patient"
)
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prescriptionId;

    @Column(name = "consultation_date", nullable = false)
    @Temporal(TemporalType.DATE) //To get only date part from timestamp and it is in sql format
    private Date consultationDate;

    @Column (name = "medical_findings")
    private String medical_findings;  //It is observation

    @ElementCollection  // DB Normalize. make separate table for medicine.
    @CollectionTable(name = "medicines", joinColumns = @JoinColumn(name = "prescriptionId"))
    private Set<String> medicine;

    @Column(name = "remark")
    private String remark;


    @Column(name = "follow_up_date")
    @Temporal(TemporalType.DATE)
    private Date followUpDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor", referencedColumnName = "doctor_id")
    private Doctor doctor;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient", referencedColumnName = "patient_id")
    private Patient patient;

    public Prescription(Date consultationDate, String medical_findings,Set<String> medicine, String remark, Date followUpDate,Doctor doctor, Patient patient) {
        this.consultationDate = consultationDate;
        this.medical_findings = medical_findings;
        this.medicine = medicine;
        this.remark = remark;
        this.followUpDate=followUpDate;
        this.doctor = doctor;
        this.patient = patient;


    }
}

