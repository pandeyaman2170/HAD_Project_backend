package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Doctor> doctors;

    @ManyToMany(mappedBy = "departments",cascade = CascadeType.ALL)
    private List<Hospital> hospitals;

}
