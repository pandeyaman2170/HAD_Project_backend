package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Entity
@Table(name = "hospitals")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospital_id;

    private String name;
    private String location;

    @ManyToOne
    @JoinColumn(name = "global_admin_id")  // Foreign key column in hospitals table
    private GlobalAdmin admin;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Doctor> doctors;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Department> departments;
}
