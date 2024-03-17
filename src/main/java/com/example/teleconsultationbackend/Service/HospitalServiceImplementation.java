package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.GlobalAdminInterface;
import com.example.teleconsultationbackend.Repository.HospitalInterface;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

//    @Override
//    @Transactional(readOnly = true)
//    public List<Hospital> getAllHospitals(Long adminId) {
//        return hospitalInterface.findByAdminId(adminId);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Hospital getHospitalById(Long adminId, Long hospitalId) {
//        return hospitalInterface.findById(hospitalId, adminId)
//                .orElseThrow(() -> new IllegalArgumentException("Hospital with id " + hospitalId + " not found for admin with id " + adminId));
//    }

    @Override
    @Transactional
    public List<Hospital> viewAllHospital(Long admin_id)
    {
        return hospitalInterface.findByAdminId(admin_id);
    }

    @Override
    @Transactional
    public void updateHospital(Long admin_id, Long hospital_id, Hospital updatedHospital)
    {
        Hospital hospital = hospitalInterface.findByIdAndGlobalAdminId(hospital_id,admin_id);
        hospital.setName(updatedHospital.getName());
        hospital.setLocation(updatedHospital.getLocation());
        hospitalInterface.save(hospital);
    }

    @Override
    @Transactional
    public void deleteHospital(Long admin_id, Long hospital_id)
    {
        hospitalInterface.delete_hospital_by_id(hospital_id,admin_id);

    }


}
