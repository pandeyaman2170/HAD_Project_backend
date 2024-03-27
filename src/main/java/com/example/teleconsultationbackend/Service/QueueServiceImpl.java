package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Doctor> getAllDoctorsFromDepartment(Long dep_id) {
        List<Doctor> allDoctors = departmentRepository.findDepartmentById(dep_id).getDoctors();
        List<Doctor> onlineDoctors = new ArrayList<>();
        for(Doctor doctor : allDoctors) {
            if(doctor.isOnline_status()) {
                onlineDoctors.add(doctor);
            }
        }
        return onlineDoctors;
    }
}
