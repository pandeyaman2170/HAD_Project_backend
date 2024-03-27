package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
@Entity
@Table(name = "queues")
public class Queues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queues_id")
    private Long queue_id;

    @JsonIgnore
    @OneToMany(mappedBy = "queues", cascade = CascadeType.ALL)
    private List<Patient> patients;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

}
