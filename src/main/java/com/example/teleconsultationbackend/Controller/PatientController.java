package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.OnlyPatientDetails;
import com.example.teleconsultationbackend.DTO.PatientDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Queues;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.QueuesRepository;
import com.example.teleconsultationbackend.Security.EncryptionService;
import com.example.teleconsultationbackend.Service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("patient")
@Tag(
        name="Patient APIs"
)
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private QueuesRepository queuesRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Wrapper class for both User and otpFlag
    @Getter
    public static class RegistrationRequest {
        private User user;
        private String height;
        private String weight;
        private String blood_group;
        private String aadhar_no;
        private boolean otpFlag;
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/register-patient")
    public boolean registerPatient(@RequestBody RegistrationRequest registrationRequest) throws Exception {
        User user1 = registrationRequest.getUser();
        User user = new User();
        OnlyPatientDetails onlyPatientDetails = new OnlyPatientDetails();
        user.setAddress(user1.getAddress());
        user.setDob(user1.getDob());
        user.setCity(user1.getCity());
        user.setEmail(user1.getEmail());
        user.setGender(user1.getGender());
        user.setPhone(user1.getPhone());
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setTitle(user1.getTitle());
        user.setPincode(user1.getPincode());
        user.setPhone(user1.getPhone());
        onlyPatientDetails.setHeight(registrationRequest.getHeight());
        onlyPatientDetails.setWeight(registrationRequest.getWeight());
        onlyPatientDetails.setBlood_group(registrationRequest.getBlood_group());
        onlyPatientDetails.setAadhar_number(registrationRequest.getAadhar_no());
        user.setRole("patient");


        boolean otpFlag = registrationRequest.isOtpFlag();

        if (otpFlag) {
            patientService.registerPatient(user,onlyPatientDetails);
        } else {
            throw new IllegalArgumentException("OTP Not verified");
        }
        return true;

    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping("/updatePatient/{patientId}")
    public PatientDetails updatePatient(@RequestBody PatientDetails patientDetails,
                                        @PathVariable String patientId) {
        return patientService.updatePatient(patientDetails, Long.parseLong(patientId));
    }


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/joinQueue/{dep_id}/{pid}")
    public void joinQueue(@PathVariable Long pid, @PathVariable Long dep_id){
        Patient patient = patientRepository.findPatientById(pid);
        System.out.println("patientName : " + patient.getUser().getFirstName());
        patientService.joinQueue(patient, dep_id);
        Department department = departmentRepository.findDepartmentById(dep_id);
        Queues queues = queuesRepository.findQueueByDepartment(department);
        if(!queues.getPatients().isEmpty()) {
            Long pidFirst = queues.getPatients().get(0).getId();
            simpMessagingTemplate.convertAndSend("/topic/call-incoming/1", pidFirst);
        }
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @DeleteMapping ("/delete-from-queue/{pid}")
    public void leftQueue(@PathVariable Long pid){
        Patient patient = patientRepository.findPatientById(pid);
        Queues queues = patient.getQueues();
        patientService.deletePatientFromQueue(pid);
        if(!queues.getPatients().isEmpty()) {
            Patient newPatient = queues.getPatients().get(0);
            simpMessagingTemplate.convertAndSend("/topic/call-incoming/1", newPatient.getId());
        } else {
            simpMessagingTemplate.convertAndSend("/topic/call-incoming/1", -1);
        }
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("getPatient/{patientId}")
    public Patient getPatient(@PathVariable String patientId) {
        return patientService.getPatientByPatientId(Long.parseLong(patientId));
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPatientByPhoneNumber/{phoneNumber}")
    public PatientDetails getPatientByPhoneNumber(@PathVariable String phoneNumber) {
        return patientService.getPatientByPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("getPatientDetailsForConsultation/{patientId}")
    public PatientDetails getPatientDetailsForConsultation(@PathVariable String patientId) {
        return patientService.getPatientDetailsForConsultation(Long.parseLong(patientId));
    }






}
