package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Consultation;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.ShareRecordHospital;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.ShareRecordRepository;
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

}
