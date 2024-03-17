package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="SharedRecord")
public class ShareRecordHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long record_id;
}
