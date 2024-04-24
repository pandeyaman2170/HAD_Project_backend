package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Consultation;
import com.example.teleconsultationbackend.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
    @Query("SELECT count(*) FROM Consultation c")
    int get_count();

    @Query("SELECT count(c.patient) FROM Consultation c")
    int distinctPatient();

    @Query("SELECT count(*) FROM Consultation c where c.doctor.id=:doctorId")
    Long findAllByDoctor_DoctorId(Long doctorId);

    List<Consultation> findConsultationsByDepIdAndStatus(Long depId, String status);

    @Query("SELECT c from Consultation c where c.doctor.id=:doctorId" )
    List<Consultation> findConsultationByDoctor_Id(Long doctorId);

    @Query("SELECT count(*) FROM Consultation c where c.patient.id=:patientId")
    Long findAllByPatientId(Long patientId);
}
