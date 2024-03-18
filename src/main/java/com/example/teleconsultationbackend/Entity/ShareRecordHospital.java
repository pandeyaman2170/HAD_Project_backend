package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="SharedRecord")
public class ShareRecordHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long record_id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital sending_hospital;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital receiving_hospital;
}
