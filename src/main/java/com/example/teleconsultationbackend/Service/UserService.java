package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.UserLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import com.example.teleconsultationbackend.Security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    public UserLoginStatus login(String phone, String role) throws Exception {
//        String key = EncryptionService.generateRandomKey();
//        String encodedPhoneNumber = EncryptionService.encrypt(phone, key); // Replace yourEncryptionKey with the actual encryption key
//        System.out.println(encodedPhoneNumber);
        // Check if the encrypted phone number is present in the database for the given role
//        User user = userRepository.findByEncodedPhoneAndRole(encodedPhoneNumber, role);
        User user = userRepository.findByPhoneAndRole(phone, role);
//        List<User> users = userRepository.getUsersDetails();
//        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//        for (User tempUser : users) {
//            // Do something with the user object
//            String phoneNumber = tempUser.getPhone();
//            if(bcrypt.matches(phone, phoneNumber) ) {
//                user = tempUser;
//                System.out.println("User ID: " + tempUser.getId());
//                System.out.println("User Name: " + phoneNumber);
//                System.out.println("User Email: " + tempUser.getEmail());
//                break;
//            }
//        }

        UserLoginStatus userLoginStatus = new UserLoginStatus();
        if(user != null) {
            if(role.equals("patient")) {
                userLoginStatus.setId(patientRepository.findPatientByUserId(user.getId()).getId());
                userLoginStatus.setIsValid(true);
            }else if(role.equals("doctor")){
                if(user.getDoctor().getAccount_status().equals("active"))
                { userLoginStatus.setId(doctorRepository.findDoctorByUserId(user.getId()).getId());
                userLoginStatus.setIsValid(true);}
            }
        }else {
            userLoginStatus.setIsValid(false);
            userLoginStatus.setId(null);
        }
        return userLoginStatus;
    }

    public Long getTotalUsersCount(){

        return userRepository.getTotalUserCount();
    }

    public List<User> getTotalUsersDetails() throws Exception {
        List<User> users = userRepository.getUsersDetails();

        for (User user : users) {
            // Do something with the user object
            String phoneNumber = user.getPhone();
            if(user.getId()==7){
                String key = EncryptionService.generateRandomKey(); // Generate a random key
                phoneNumber = EncryptionService.decrypt(phoneNumber, key);
            }
            System.out.println("User ID: " + user.getId());
            System.out.println("User Name: " + phoneNumber);
            System.out.println("User Email: " + user.getEmail());
            // ...
        }

        return users;
    }
}