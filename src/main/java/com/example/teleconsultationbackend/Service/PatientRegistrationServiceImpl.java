package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional
    public void registerPatient(User user){
        Patient patient = new Patient();
        patient.setUser(user);
        patientRepository.save(patient);
        System.out.println("Done Creating User");
    }
}
