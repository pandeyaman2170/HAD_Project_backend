package com.example.teleconsultationbackend.Service;


import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // --------------------------------- Add Doctor to Database -------------------------------------
    @Transactional
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
            user.setRole(doctorDetails.getRole());
            user.setPhone(doctorDetails.getPhoneNumber());
            user.setTitle(doctorDetails.getTitle());
            doctor.setRegistrationNumber(doctorDetails.getRegistration_number());
            doctor.setUser(user);
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
}
