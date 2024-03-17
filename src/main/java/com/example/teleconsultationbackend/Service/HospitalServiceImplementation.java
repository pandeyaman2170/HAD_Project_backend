package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.GlobalAdminInterface;
import com.example.teleconsultationbackend.Repository.HospitalInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HospitalServiceImplementation implements HospitalService {
    @Autowired
    HospitalInterface hospitalInterface;

    @Autowired
    GlobalAdminInterface globalAdminInterface;

    @Override
    @Transactional
    public void createHospital(Long admin_id, Hospital hospital) {
        Optional<GlobalAdmin> optionalGlobalAdmin = globalAdminInterface.findById(admin_id);
        if (optionalGlobalAdmin.isPresent()) {
            GlobalAdmin globalAdmin = optionalGlobalAdmin.get();
            hospital.setAdmin(globalAdmin);
            hospitalInterface.save(hospital);
        } else {
            // Handle the case where GlobalAdmin with given ID is not found
            throw new IllegalArgumentException("GlobalAdmin with id " + admin_id + " not found.");
        }
    }
}
