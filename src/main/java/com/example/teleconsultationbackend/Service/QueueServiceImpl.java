package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
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

    @Autowired
    private PatientRepository patientRepository;

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
    public int getAllWaitingPatientByInQueueByDepartmentId(Long pid) {
        Patient patient = patientRepository.findPatientById(pid);

        Queues queues = patient.getQueues();

        if(queues != null && queues.getPatients() != null) {
            int index = queues.getPatients().indexOf(patient);
            return index + 1;
        }
        return 0;
    }

    @Override
    public List<Patient> getAllPatientByDepNameQueue(String depName){
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(queues != null && queues.getPatients() != null)
            return queues.getPatients();
        return null;
    }

    @Override
    public Long getQueuesTop(String depName){
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(queues != null && !queues.getPatients().isEmpty()){
            return queues.getPatients().get(0).getId();
        }
        return null;
    }

    @Override
    public int getQueueSizeHelper(String depName) {
        Department department = departmentRepository.findDepartmentByName(depName);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(queues != null && queues.getPatients() != null)
            return queues.getPatients().size();
        return -1;
    }
}
