package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.User;

public interface PatientService {
    void registerPatient(User user);
    void joinQueue(Patient patient, Long dep_id);
    void deletePatientFromQueue(Long pid);
}
