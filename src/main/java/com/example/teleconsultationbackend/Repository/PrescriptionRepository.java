package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    @Query("SELECT p FROM Prescription p WHERE p.patient.id = ?1")
    List<Prescription> findPrescriptionsByPatient_PatientId(long patient_id);


    @Query("SELECT p FROM Prescription p WHERE p.prescriptionId = ?1")
    Prescription findPrescriptionByPrescriptionId(int prescriptionId);

    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = ?1")
    public List<Prescription> findPrescriptionsByDoctor_DoctorId(long doctorId);



}
