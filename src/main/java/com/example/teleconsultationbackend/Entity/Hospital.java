package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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
    @JsonIgnore
    private GlobalAdmin admin;

}
