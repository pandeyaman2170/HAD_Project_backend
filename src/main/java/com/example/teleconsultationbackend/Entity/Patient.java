package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
@Schema(
        description = "Store the patient details"
)
public class Patient{
    // Additional patient-specific attributes or methods can be added here
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @Column(name = "blood_group",nullable = false)
    private String blood_group;
    @Column(name = "height",nullable = false)
    private String height;
    @Column(name = "weight",nullable = false)
    private String weight;
    @Column(name = "aadhar_no",nullable = false,unique = true)
    private String aadhar_number;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queues queues;
}
