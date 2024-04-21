package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Doctors")
@Schema(
        description = "Store the Doctor details"
)
public class Doctor{
    // Additional doctor-specific attributes or methods can be added here

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    @Column(name = "aadhar_no",nullable = false,unique = true)
    private String aadhar_number;

    @Column(name = "registrationNumber",nullable = false,unique = true)
    private String registrationNumber;

    @Column(name = "online_status",nullable = false)
    private boolean online_status;

    @Column(name = "role",nullable = false)
    private String role;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;
}