package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;

import java.util.List;

public interface QueueService {
    List<Doctor> getAllDoctorsFromDepartment(Long dep_id);

    int getAllWaitingPatientByInQueueByDepartmentId(Long dep_id);

    List<Patient> getAllPatientByDepNameQueue(String depName);

    Long getQueuesTop(String depName);

    int getQueueSizeHelper(String depName);
}
