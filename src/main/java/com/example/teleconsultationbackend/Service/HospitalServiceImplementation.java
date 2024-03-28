package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImplementation implements HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    GlobalAdminRepository globalAdminRepository;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private QueuesRepository queuesRepository;

    @Autowired
    ConsultationRepository consultationRepository;
    @Override
    public int total_hospitals()
    {
        return hospitalRepository.get_count();
    }

    @Override
    @Transactional
    public void createHospital(Long admin_id, Hospital hospital) {
        Optional<GlobalAdmin> optionalGlobalAdmin = globalAdminRepository.findById(admin_id);
        if (optionalGlobalAdmin.isPresent()) {
            GlobalAdmin globalAdmin = optionalGlobalAdmin.get();
            hospital.setAdmin(globalAdmin);
            hospitalRepository.save(hospital);
        } else {
            // Handle the case where GlobalAdmin with given ID is not found
            throw new IllegalArgumentException("GlobalAdmin with id " + admin_id + " not found.");
        }
    }

    @Override
    @Transactional
    public void addDepartments(Long hospitalId, String departmentName){
        Department department1 = departmentRepository.findDepartmentByName(departmentName);
        if(department1 == null) {
            Optional<Hospital> optionalHospital = hospitalRepository.findById(hospitalId);
            if (optionalHospital.isPresent()) {
                Hospital hospital = optionalHospital.get();
                Department department = new Department();
                department.setName(departmentName);
                hospital.getDepartments().add(department);
                departmentRepository.save(department);
                if (queuesRepository.findQueueByDepartment(department) != null) {
                    System.out.println("The Queue Already created for this department");
                } else {
                    System.out.println("creating the queue");

                    Queues queues = new Queues();
                    queues.setDepartment(department);
                    department.setQueues(queues);
                    queuesRepository.save(queues);
                    System.out.println("created the queue for department : " + department.getName());
                }
            }
        } else {
            System.out.println("department already exists");
        }
    }

    public String adminlogin(String phone) {
        Hospital hospital = hospitalRepository.findByPhone(phone);
        if (hospital != null) {
            return "OK";
        } else {
            return "Error: User not found";
        }
    }

    @Override
    @Transactional
    public List<Department> getAllDepartments(Long hospital_id){
//        Hospital hospital = hospitalRepository.getReferenceById()
////        System.out.println("111111111111111:  " + hospital);
//        return hospitalRepository.getReferenceById(hospital_id).getDepartments();
        Hospital hospital = hospitalRepository.getHospitalsByHospital_id(hospital_id);
        System.out.println("111111111111111:  " + hospital.getDepartments());
        return hospital.getDepartments();
    }
}
