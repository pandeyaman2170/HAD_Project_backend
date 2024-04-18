package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class Consultation1ServiceImplementation implements Consultation1Service{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private Consultation1Repository consultation1Repository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private QueuesRepository queuesRepository;
    @Override
    public void addConsultationStatusWaitinghelper(Long patientId, Long depId) {
        Patient patient =  patientRepository.findPatientById(patientId);
        consultation1Repository.save(
          new Consultation1(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                  null, patient,
                  "waiting",
                  depId)
        );
    }

    @Override
    public void setStatusToAcceptedHelper(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        Long depId = doctor.getDepartment().getId();
        for (Consultation1 consultation1 : consultation1Repository.findAll()){
            if (Objects.equals(consultation1.getDepId(), depId) &&
                    Objects.equals(consultation1.getStatus(), "waiting")){
                consultation1.setDoctor(doctor);
                consultation1.setStatus("accepted");
                consultation1Repository.save(consultation1);
                break;
            }
        }
    }
}
