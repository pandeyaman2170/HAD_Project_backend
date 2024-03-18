package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient{
    // Additional patient-specific attributes or methods can be added here
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
