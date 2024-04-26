package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SharedRecord")
@Schema(
        description = "Share the Record between hospital with user consent"
)
public class ShareRecordHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long record_id;

    private Long patientId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital sending_hospital;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Hospital receiving_hospital;

}
