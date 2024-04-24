package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PatientDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private Consultation1Repository consultation1Repository;

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
        boolean flag = false;
        for(Queues queues1 : queuesRepository.findAll()){
            for(Patient patient1 : queues1.getPatients()){
                if (patient1.equals(patient)){
                    System.out.println("patient already present in the " + department.getName() + " queue");
                    flag = true;
                    break;
                }
            }
        }
        if(!flag) {
            patient.setQueues(queues);
            queues.getPatients().add(patient);
            patientRepository.save(patient);
            consultation1Repository.save(
                    new Consultation1(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            null, patient,
                            "waiting",
                            dep_id)
            );
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
            if(queues != null) {
                queues.getPatients().remove(patient);
                patient.setQueues(null);
                for (Patient patient1 : queues.getPatients()){
                    System.out.println("patient is: " + patient1.getId());
                }
                patientRepository.save(patient);
                queuesRepository.save(queues);
                System.out.println("patient deleted from the queue");
            }else{
                System.out.println("patient is not in the queue");
            }
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
    public PatientDetails getPatientDetailsForConsultation(Long patientId)
    {
        Patient patient = patientRepository.getPatientDetailsForConsultation(patientId);
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setPatientId(patient.getId());
        patientDetails.setFirstName(patient.getUser().getFirstName());
        patientDetails.setDob(patient.getUser().getDob());
        patientDetails.setGender(patient.getUser().getGender());
        patientDetails.setPhoneNo(patient.getUser().getPhone());
        return patientDetails;
    }

}
