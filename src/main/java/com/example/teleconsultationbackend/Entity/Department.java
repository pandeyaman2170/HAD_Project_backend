package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="Department")
@Schema(
        description = "Store the Department details"
)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Doctor> doctors;

    @JsonIgnore
    @ManyToMany(mappedBy = "departments",cascade = CascadeType.ALL)
    private List<Hospital> hospitals;

    @JsonIgnore
    @OneToOne(mappedBy = "department", cascade = CascadeType.ALL)
    private Queues queues;
}
