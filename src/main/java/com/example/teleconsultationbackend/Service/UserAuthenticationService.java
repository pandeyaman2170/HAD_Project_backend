package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.GlobalAdminRepository;
import com.example.teleconsultationbackend.Repository.HospitalRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private GlobalAdminRepository globalAdminRepository;

    @Autowired
    private HospitalRepository hospitalRepository;


    private Set getAuthorityPatient(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
        return authorities;
    }

    private Set getAuthorityDoctor(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        return authorities;
    }

    private Set getAuthorityGlobalAdmin(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GLOBALADMIN"));
        return authorities;
    }

    private Set getAuthorityHospitalAdmin(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_HOSPITALADMIN"));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber){
        // Check if user is a doctor
        Doctor doctor = doctorRepository.findByPhoneNumber(phoneNumber);
        if (doctor != null) {
            return new User(doctor.getUser().getPhone(), doctor.getUser().getPhone(), getAuthorityDoctor());
        }

        // Check if user is a patient
        Patient patient = patientRepository.findByPhoneNo(phoneNumber);
        if (patient != null) {
            return new User(patient.getUser().getPhone(), patient.getUser().getPhone(), getAuthorityPatient());
        }

        GlobalAdmin globalAdmin = globalAdminRepository.findGlobalAdminByUserName(phoneNumber);
        //check if gloablAdmin present
        if(globalAdmin!=null){
            return new User(globalAdmin.getGlobalAdminUsername(), globalAdmin.getGlobalAdminPassword(), getAuthorityGlobalAdmin());
        }

        Hospital hospital = hospitalRepository.findHospitalsByHospitalUserName(phoneNumber);
        if(hospital!=null){
            return new User(hospital.getHospitalUserName(), hospital.getHospitalPassword(), getAuthorityHospitalAdmin());
        }

        throw new UsernameNotFoundException("User Not Found");
    }

}
