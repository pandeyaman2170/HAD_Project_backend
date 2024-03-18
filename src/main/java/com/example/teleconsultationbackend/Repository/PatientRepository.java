package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientById(Long id);
}
