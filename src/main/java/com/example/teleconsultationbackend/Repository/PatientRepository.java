package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientById(Long id);
    Patient findPatientByUserId(Long id);

   // Patient findByEmail(String email);


    @Query("SELECT p FROM Patient p WHERE p.user.phone = ?1")
    Patient findByPhoneNo(String phoneNo);
    //public Patient findByPhoneNo(String phoneNo);

    @Query("SELECT count(*) FROM Patient p")
    int get_count();
}

