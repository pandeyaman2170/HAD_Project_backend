package com.example.teleconsultationbackend.Service;


import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImplementation implements DoctorService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public int total_doctors()
    {
        return doctorRepository.get_count();
    }


    // --------------------------------- Add Doctor to Database -------------------------------------

    @Override
    public DoctorDetails addDoctor(DoctorDetails doctorDetails) {

        try {
            Department department = departmentRepository.findDepartmentByName(doctorDetails.getDepartmentName());
            Hospital hospital = hospitalRepository.getHospitalsByHospital_id(doctorDetails.getHospitalID());

            Doctor doctor = new Doctor();
            doctor.setDepartment(department);
            doctor.setHospital(hospital);

            User user = new User();
            user.setCity(doctorDetails.getCity());
            user.setDob(doctorDetails.getDob());
            user.setEmail(doctorDetails.getEmail());
            user.setGender(doctorDetails.getGender());
            user.setAddress(doctorDetails.getAddr());
            user.setDob(doctorDetails.getDob());
            user.setFirstName(doctorDetails.getFirstName());
            user.setLastName(doctorDetails.getLastName());
            user.setCity(doctorDetails.getCity());
            user.setPincode(doctorDetails.getPincode());
            user.setRole("doctor");
            user.setPhone(doctorDetails.getPhoneNumber());
            user.setTitle(doctorDetails.getTitle());
            doctor.setRegistrationNumber(doctorDetails.getRegistration_number());
            doctor.setRole(doctorDetails.getRole());
            doctor.setUser(user);
            doctor.setAadhar_number(doctorDetails.getAadhar_number());
            userRepository.save(user);
            doctorRepository.save(doctor);
            // Saving the online doctors to the database

            return doctorDetails;
        } catch (Exception e) {
            System.out.println("Error Occurred while adding doctor to the database");
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public DoctorFetchDetails getDoctorByPhoneNumber(String phoneNumber) {
        try {
            Doctor doctor = doctorRepository.findByPhoneNumber(phoneNumber);
            if (doctor != null) {
                return new DoctorFetchDetails(
                        doctor.getId(),
                        doctor.getUser().getTitle(),
                        doctor.getUser().getFirstName(), doctor.getUser().getLastName(),
                        doctor.getUser().getEmail(), doctor.getUser().getPhone(),
                        doctor.getRegistrationNumber(), doctor.getUser().getDob(),
                        doctor.getUser().getGender(), doctor.getUser().getAddress(),
                        doctor.getUser().getCity(), doctor.getUser().getPincode(),
                        doctor.getDepartment().getName(),doctor.getHospital().getHospital_id(),doctor.getRole()

                );
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error Occurred while verifying phone number");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DoctorFetchDetails updateDoctorDetails(DoctorFetchDetails doctorDetails){
        System.out.println("doctor details: "+doctorDetails);
        Doctor doctor = doctorRepository.findByPhoneNumber(doctorDetails.getPhoneNumber());
        doctor.getUser().setFirstName(doctorDetails.getFirstName());
        doctor.getUser().setLastName(doctorDetails.getLastName());
        doctor.getUser().setPincode(doctorDetails.getPincode());
        doctor.getUser().setCity(doctorDetails.getCity());
        doctor.getUser().setAddress(doctorDetails.getAddr());
        doctor.getUser().setTitle(doctorDetails.getTitle());
        doctor.getUser().setEmail(doctorDetails.getEmail());

        doctorRepository.save(doctor);
        return doctorDetails;
    }

    @Override
    public void setDoctorOnlineStatusHelper(Long doctorId) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        doctor.setOnline_status(true);
        doctorRepository.save(doctor);
    }

    @Override
    public void setDoctorOfflineStatusHelper(Long doctorId) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        doctor.setOnline_status(false);
        doctorRepository.save(doctor);
    }
}
