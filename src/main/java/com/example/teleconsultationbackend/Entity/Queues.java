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

//    private Boolean isAccepted;
//    private Boolean isPresent;

    @OneToMany(mappedBy = "queues", cascade = CascadeType.ALL)
    private List<Patient> patients;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

}
