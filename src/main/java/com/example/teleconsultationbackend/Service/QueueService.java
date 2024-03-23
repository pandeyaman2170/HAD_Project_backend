package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;

import java.util.List;

public interface QueueService {
    List<Doctor> getAllDoctorsFromDepartment(Long dep_id);
}
