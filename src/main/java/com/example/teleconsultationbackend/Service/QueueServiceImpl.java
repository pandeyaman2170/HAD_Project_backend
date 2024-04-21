package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.QueuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private QueuesRepository queuesRepository;

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

    @Override
    public int getAllWaitingPatientByInQueueByDepartmentId(Long dep_id) {
        Department department = departmentRepository.findDepartmentById(dep_id);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        return queues.getPatients().size();
    }

    @Override
    public List<Patient> getAllPatientByDepNameQueue(String depName){
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        return queues.getPatients();
    }

    @Override
    public Long getQueuesTop(String depName){
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(!queues.getPatients().isEmpty()){
            return queues.getPatients().get(0).getId();
        }
        return null;
    }

    @Override
    public int getQueueSizeHelper(String depName) {
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        return queues.getPatients().size();
    }
}
