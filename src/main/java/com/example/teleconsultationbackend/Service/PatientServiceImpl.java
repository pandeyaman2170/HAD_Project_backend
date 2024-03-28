package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PatientDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.QueuesRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueuesRepository queuesRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public  int total_patients(){
        return patientRepository.get_count();
    }

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

    @Override
    public Patient getPatientByPatientId(long patientId) {
        try {
            if(patientRepository.findById(patientId).isPresent())
                return patientRepository.findById(patientId).get();
            else return null;
        } catch (Exception e) {
            System.out.println("Error Occurred while fetching patient by patient Id");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PatientDetails updatePatient(PatientDetails patientDetails, long patientId) {
        Patient updatedPatient = patientRepository.findPatientById(patientId);
        updatedPatient.getUser().setTitle(patientDetails.getTitle());
        updatedPatient.getUser().setFirstName(patientDetails.getFirstName());
        updatedPatient.getUser().setLastName(patientDetails.getLastName());
        updatedPatient.getUser().setPincode(patientDetails.getPincode());
        updatedPatient.getUser().setCity(patientDetails.getCity());
        updatedPatient.getUser().setAddress(patientDetails.getAddr());
        updatedPatient.getUser().setPhone(patientDetails.getPhoneNo());
        updatedPatient.getUser().setGender(patientDetails.getGender());
        updatedPatient.getUser().setEmail(patientDetails.getEmail());
        updatedPatient.getUser().setDob(patientDetails.getDob());
        patientRepository.save(updatedPatient);
        patientDetails.setPatientId(patientId);
        return patientDetails;
    }

    @Override
    public PatientDetails getPatientByPhoneNumber(String phoneNumber) {
        try {
            Patient patient = patientRepository.findByPhoneNo(phoneNumber);

//                public PatientDetails(long patientId, String title, String firstName, String lastName, String gender, String phoneNo, String email, Date
//            dob, String addr, String city, long pincode) {
//                this.patientId = patientId;
//                this.title = title;
//                this.firstName = firstName;
//                this.lastName = lastName;
//                this.gender = gender;
//                this.phoneNo = phoneNo;
//                this.email = email;
//                this.dob = dob;
//                this.addr = addr;
//                this.city = city;
//                this.pincode = pincode;
//            }

            if (patient != null) {
                return new PatientDetails(patient.getId(),patient.getUser().getTitle(),patient.getUser().getFirstName(),patient.getUser().getLastName(),
                        patient.getUser().getGender(),patient.getUser().getPhone(),patient.getUser().getEmail(),patient.getUser().getDob(),
                        patient.getUser().getAddress(),patient.getUser().getCity(),patient.getUser().getPincode()
                );
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error Occurred while verifying phone number");
            e.printStackTrace();
            return null;
        }
    }

}
