package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "hospital_id")
//    private Hospital hospital;


}
