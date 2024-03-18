package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HospitalServiceImplementation implements HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    GlobalAdminRepository globalAdminRepository;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserRepository userRepository;


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
    public void addDoctor(Long hospital_id, Doctor doctor) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospital_id);
        if(optionalHospital.isPresent()){
            Hospital hospital=optionalHospital.get();
            doctor.setHospital(hospital);
            User user = doctor.getUser();
            if(user.getId()==null){
                userRepository.save(user);
            }
            doctorRepository.save(doctor);
        }
        else{
            throw new IllegalArgumentException("Hospital with id " + hospital_id + " not found.");
        }

    }
}
