package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PatientDetails {

    private long patientId;

    private String title;

    private String firstName;

    private String lastName;

    private char gender;

    private String phoneNo;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String addr;

    private String city;

    private long pincode;

//    private List<Prescription> prescriptionList;
    // --------------------------------- Constructor ---------------------------------------

    public PatientDetails() {
    }

    public PatientDetails(long patientId, String title, String firstName, String lastName, char gender, String phoneNo, String email, Date dob, String addr, String city, long pincode) {
        this.patientId = patientId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.dob = dob;
        this.addr = addr;
        this.city = city;
        this.pincode = pincode;
    }

    // ------------------------------------ toString() method ---------------------------------------

    @Override
    public String toString() {
        return "PatientDetails{" +
                "patientId=" + patientId +
                ", title='" + title + '\'' +
                ", fName='" + firstName + '\'' +
                ", lName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNo=" + phoneNo +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", addr='" + addr + '\'' +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                '}';
    }
}
