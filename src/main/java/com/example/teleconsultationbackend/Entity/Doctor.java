package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Doctors")
public class Doctor{
    // Additional doctor-specific attributes or methods can be added here

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    private String registrationNumber;
    private  String department;
    private boolean online_status;


//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "hospital_id")
//    private Hospital hospital;

}