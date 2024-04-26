package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareRecordImplementation implements ShareRecordService{
    @Autowired
    private ShareRecordRepository shareRecordRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    ConsultationRepository consultationRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    @Transactional
    public void createShareRecord(Hospital sending, Hospital receiving){
        System.out.println(" Sending Hospital Details : ");
        System.out.println("name: " + sending.getName());
        System.out.println("location: " + sending.getLocation());
        System.out.println("_______________________________________");
        System.out.println(" Receiving Hospital Details : ");
        System.out.println("name: " + receiving.getName());
        System.out.println("location: " + receiving.getLocation());
        System.out.println("_______________________________________");

        ShareRecordHospital shareRecordHospital = new ShareRecordHospital();
        shareRecordHospital.setSending_hospital(sending);
        shareRecordHospital.setReceiving_hospital(receiving);
        shareRecordRepository.save(shareRecordHospital);
        System.out.println("Done with creating record");
    }

    @Override
    @Transactional
    public void addRecordHelper(Long patientId, Long doctorId){
        List<Prescription> prescriptionList = prescriptionRepository.findPrescriptionsByPatient_PatientId(patientId);
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        Hospital recievingHospital = doctor.getHospital();
        for (Prescription prescription : prescriptionList){
            Hospital sendingHospital = prescription.getDoctor().getHospital();
            if (sendingHospital != recievingHospital){
                ShareRecordHospital shareRecordHospital = shareRecordRepository.
                        findShareRecordHospitalByPatientIdAndSending_hospitalAndReceiving_hospital(
                                patientId, sendingHospital, recievingHospital);
                if(shareRecordHospital == null){
                    ShareRecordHospital shareRecordHospital1 = new ShareRecordHospital();
                    shareRecordHospital1.setPatientId(patientId);
                    shareRecordHospital1.setSending_hospital(sendingHospital);
                    shareRecordHospital1.setReceiving_hospital(recievingHospital);
                    shareRecordRepository.save(shareRecordHospital1);
                }
            }
        }
    }
}
