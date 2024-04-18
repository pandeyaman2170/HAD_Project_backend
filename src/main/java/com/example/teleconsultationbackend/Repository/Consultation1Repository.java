package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Consultation1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Consultation1Repository extends JpaRepository<Consultation1,Long> {
    @Query("SELECT c FROM Consultation1 c WHERE c.patient.id=?1 AND c.status='waiting'")
    Consultation1 getWaitingPatientById(Long patientId);
}
