package com.example.backadmin.Services;

import com.example.backadmin.Entity.Doctor;
import com.example.backadmin.Entity.Hospital;
import com.example.backadmin.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {return doctorRepository.findAll();}
    public Optional<Doctor> getDoctorById(Long id) {return doctorRepository.findById(id);}
    public Doctor createDoctor(Doctor doctor) {
        System.out.println("****************************");
        return doctorRepository.save(doctor);

    }
    public Doctor updateDoctor(Doctor updatedDoctor, Long id) {
        Optional<Doctor> existingDoctorOptional = doctorRepository.findById(id);

        if (existingDoctorOptional.isPresent()) {
            Doctor existingDoctor = existingDoctorOptional.get();
            existingDoctor.setFirstName(updatedDoctor.getFirstName());
            existingDoctor.setLastName(updatedDoctor.getLastName());
            existingDoctor.setTitle(updatedDoctor.getTitle());
            existingDoctor.setDob(updatedDoctor.getDob());
            existingDoctor.setRegistrationNumber(updatedDoctor.getRegistrationNumber());
            existingDoctor.setMobileNumber(updatedDoctor.getMobileNumber());
            existingDoctor.setGender(updatedDoctor.getGender());
            existingDoctor.setAddress(updatedDoctor.getAddress());
            existingDoctor.setCity(updatedDoctor.getCity());
            existingDoctor.setState(updatedDoctor.getState());
            existingDoctor.setEmail(updatedDoctor.getEmail());
            existingDoctor.setDepartment(updatedDoctor.getDepartment());
            existingDoctor.setPincode(updatedDoctor.getPincode());
//            existingDoctor.setHospitalId(updatedDoctor.getHospitalId());

            return doctorRepository.save(existingDoctor);
        } else {
            throw new RuntimeException("Hospital not found with id: " + id);
        }
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }



}
