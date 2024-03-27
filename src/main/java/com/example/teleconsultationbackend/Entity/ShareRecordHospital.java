package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital sending_hospital;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital receiving_hospital;
}
