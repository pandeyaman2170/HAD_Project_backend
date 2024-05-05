package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorFetchDetails {
    private long doctorId;

    private String title;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String registration_number;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private char gender;

    private String addr;

    private String city;

    private Long pincode;

    private String departmentName;

    private Long hospitalID;

    private String role;

    private String accountStatus;
    // ------------------------------- toString() method -------------------------------

    @Override
    public String toString() {
        return "DoctorDepartment{" +
                ", title='" + title + '\'' +
                ", fName='" + firstName + '\'' +
                ", lName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", registration_number='" + registration_number + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", addr='" + addr + '\'' +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                ", dept_name='" + departmentName + '\'' +
                '}';
    }
}
