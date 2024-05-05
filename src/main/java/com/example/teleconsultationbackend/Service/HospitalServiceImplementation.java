package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    PrescriptionRepository prescriptionRepository;
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
        System.out.println("dddd: "+department1);
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospitalId);
        Hospital hospital = optionalHospital.get();
        if(department1 != null) {
            if (optionalHospital.isPresent()) {
                boolean flag = false;
                Department department = new Department();
                for(Department department2:hospital.getDepartments()){
                    if (department2.getId() == department1.getId()){
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
//                    department.setName(departmentName);
                    hospital.getDepartments().add(department1);
//                    departmentRepository.save(department);
                    if (queuesRepository.findQueueByDepartment(department1) != null) {
                        System.out.println("The Queue Already created for this department");
                    } else {
                        System.out.println("creating the queue");

                        Queues queues = new Queues();
                        queues.setDepartment(department1);
                        department.setQueues(queues);
                        queuesRepository.save(queues);
                        System.out.println("created the queue for department : " + department1.getName());
                    }
                } else {
                    System.out.println("Department already exist in hospital"+ hospitalId);
                    return;
                }
            }
        } else {
            Department department = new Department();
            department.setName(departmentName);
            departmentRepository.save(department);
            hospital.getDepartments().add(department);
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
            System.out.println("deparetment "+department.getId()+":"+department.getName()+ " in department table");
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
//        System.out.println("111111111111111:  " + hospital.getDepartments());
        return hospital.getDepartments();
    }
    @Override
    @Transactional
    public Hospital getHospitalByUserName(String username){
        Hospital hospital = hospitalRepository.getHospitalsByUserName(username);
        return hospital;
    }

    @Override
    @Transactional
    public List<DoctorFetchDetails> getDoctorsListOfAHospital(Long hospital_id){
        List<Doctor> doctors = doctorRepository.getDoctorsByHospitalID(hospital_id);
        List<DoctorFetchDetails> doclist = new ArrayList<>();
        for(Doctor doctor: doctors){
            DoctorFetchDetails doc = new DoctorFetchDetails();
            doc.setDoctorId(doctor.getId());
            doc.setTitle(doctor.getUser().getTitle());
            doc.setFirstName(doctor.getUser().getFirstName());
            doc.setLastName(doctor.getUser().getLastName());
            doc.setEmail(doctor.getUser().getEmail());
            doc.setHospitalID(doctor.getHospital().getHospital_id());
            doc.setPhoneNumber(doctor.getUser().getPhone());
            doc.setRegistration_number(doctor.getRegistrationNumber());
            doc.setDob(doctor.getUser().getDob());
            doc.setGender(doctor.getUser().getGender());
            doc.setAddr(doctor.getUser().getAddress());
            doc.setPincode(doctor.getUser().getPincode());
            doc.setCity(doctor.getUser().getCity());
            doc.setDepartmentName(doctor.getDepartment().getName());
            doc.setRole(doctor.getUser().getRole());
            doc.setAccountStatus(doctor.getAccount_status());
            doclist.add(doc);

//            System.out.println(doctor.getUser().getFirstName());
        }
        return doclist;
    }

    @Override
    @Transactional
    public List<Department> getHospitalDepartments(Long hospitalId){
        Hospital hospital= hospitalRepository.getHospitalsByHospital_id(hospitalId);
        List<Department> departmentsList = (hospital.getDepartments());
        return departmentsList;
    }

    @Override
    @Transactional
    public List<PrescriptionDetails> getHospitalTotalConsultations(Long hospitalId){
        List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.getDoctorsByHospitalID(hospitalId);
        List<Prescription> prescriptions = prescriptionRepository.getAllPrescription(doctors);
        for (Prescription prescription : prescriptions) {
            prescriptionDetailsList.add(new PrescriptionDetails(prescription.getPrescriptionId(),prescription.getConsultationDate(),prescription.getMedical_findings(),
                    prescription.getMedicine(),prescription.getRemark(),prescription.getDoctor().getUser().getFirstName()+" " +prescription.getDoctor().getUser().getLastName(),prescription.getDoctor().getId(),
                    prescription.getPatient().getUser().getFirstName()+" "+prescription.getPatient().getUser().getLastName(),prescription.getPatient().getId(), prescription.getFollowUpDate(),prescription.getDoctor().getHospital().getName(),prescription.getDoctor().getDepartment().getName()));
        }
        return  prescriptionDetailsList;
    }
}
