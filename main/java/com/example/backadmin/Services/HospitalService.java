package com.example.backadmin.Services;

import com.example.backadmin.Entity.Hospital;
import com.example.backadmin.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Long id) {
        return hospitalRepository.findById(id);
    }

    public Hospital createHospital(Hospital hospital) {
        System.out.println("****************************");
        return hospitalRepository.save(hospital);

    }

    public Hospital updateHospital(Hospital updatedHospital, Long id) {
        Optional<Hospital> existingHospitalOptional = hospitalRepository.findById(id);

        if (existingHospitalOptional.isPresent()) {
            Hospital existingHospital = existingHospitalOptional.get();
            existingHospital.setName(updatedHospital.getName());
            existingHospital.setLocation(updatedHospital.getLocation());
            return hospitalRepository.save(existingHospital);
        } else {
            throw new RuntimeException("Hospital not found with id: " + id);
        }
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}

