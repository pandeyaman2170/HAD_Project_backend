package com.example.backadmin.Services;

import com.example.backadmin.Entity.Doctor;
import com.example.backadmin.Entity.Hospital;
import com.example.backadmin.Repository.DoctorRepository;
import com.example.backadmin.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HospitalServiceImplementation implements HospitalService{

    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Override
    public void createDoctor(Long hospital_id, Doctor doctor) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospital_id);
        if(optionalHospital.isPresent()){
            Hospital hospital=optionalHospital.get();
            doctor.setHospital(hospital);
            doctorRepository.save(doctor);
        }
        else{
            throw new IllegalArgumentException("Hospital with id " + hospital_id + " not found.");
        }

    }
}
