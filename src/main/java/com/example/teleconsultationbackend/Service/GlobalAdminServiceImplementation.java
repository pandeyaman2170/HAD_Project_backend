package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GlobalAdminServiceImplementation implements GlobalAdminService{
    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    GlobalAdminRepository globalAdminRepository;

    @Autowired
    ConsultationRepository consultationRepository;

    @Override
    @Transactional
    public void createHospital(Long admin_id, Hospital hospital) {
        Optional<GlobalAdmin> optionalGlobalAdmin = globalAdminRepository.findById(admin_id);
        if (optionalGlobalAdmin.isPresent()) {
            GlobalAdmin globalAdmin = optionalGlobalAdmin.get();
            hospital.setAdmin(globalAdmin);
            hospitalRepository.save(hospital);
        } else {
            // Handle the case where GlobalAdmin with given ID is not found
            throw new IllegalArgumentException("GlobalAdmin with id " + admin_id + " not found.");
        }
    }

    @Override
    @Transactional
    public List<Hospital> viewAllHospital(Long admin_id)
    {
        return hospitalRepository.findByAdminId(admin_id);
    }

    @Override
    @Transactional
    public void updateHospital(Long admin_id, Long hospital_id, Hospital updatedHospital)
    {
        Hospital hospital = hospitalRepository.findByIdAndGlobalAdminId(hospital_id,admin_id);
        hospital.setName(updatedHospital.getName());
        hospital.setLocation(updatedHospital.getLocation());
        hospitalRepository.save(hospital);
    }

    @Override
    @Transactional
    public void deleteHospital(Long admin_id, Long hospital_id)
    {
        hospitalRepository.delete_hospital_by_id(hospital_id,admin_id);

    }

    @Override
    @Transactional
    public int totalHospitals()
    {
        List<Hospital> hospitals=hospitalRepository.findAll();
        return hospitals.size();
    }

    @Override
    @Transactional
    public int totalDoctors()
    {
        int totalDoctors=0;
        List<Hospital> hospitals=hospitalRepository.findAll();

        for (Hospital hospital : hospitals) {
            totalDoctors += hospital.getDoctors().size();
        }
        return totalDoctors;

    }
    @Override
    @Transactional
    public int totalPatients()
    {
        return consultationRepository.distinctPatient();
    }

    @Override
    @Transactional
    public GlobalAdmin getGlobalAdminByUserName(String userName){
        return globalAdminRepository.findGlobalAdminByUserName(userName);
    }








}
