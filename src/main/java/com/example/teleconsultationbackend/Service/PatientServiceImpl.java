package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.QueuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QueuesRepository queuesRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public void registerPatient(User user){
        Patient patient = new Patient();
        patient.setUser(user);
        patientRepository.save(patient);
        System.out.println("Done Creating User");
    }

    @Override
    @Transactional
    public void joinQueue(Patient patient, Long dep_id){
        Department department = departmentRepository.findDepartmentById(dep_id);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(queues.getPatients().contains(patient)){
            System.out.println("patient Already present in the queue");
            // handle by error
        }else {
            patient.setQueues(queues);
            queues.getPatients().add(patient);
            queuesRepository.save(queues);
        }
    }

    @Override
    @Transactional
    public void deletePatientFromQueue(Long pid){
        Patient patient = patientRepository.findPatientById(pid);
        if(patient == null){
            System.out.println("Patient not found ");
            // handle by error
        }else{
            Queues queues = patient.getQueues();
            queues.getPatients().remove(patient);
            patient.setQueues(null);
            System.out.println("patient deleted from the queue");
        }
    }
}
