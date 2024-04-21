package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.OnlyPatientDetails;
import com.example.teleconsultationbackend.DTO.PatientDetails;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.User;

public interface PatientService {
    void registerPatient(User user, OnlyPatientDetails onlyPatientDetails);
    void joinQueue(Patient patient, Long dep_id);
    void deletePatientFromQueue(Long pid);

    PatientDetails updatePatient(PatientDetails patientDetails, long patientId);

    public Patient getPatientByPatientId(long patientId);

    public PatientDetails getPatientByPhoneNumber(String phoneNumber);
    public int total_patients();

    PatientDetails getPatientDetailsForConsultation(Long patientId);
}
