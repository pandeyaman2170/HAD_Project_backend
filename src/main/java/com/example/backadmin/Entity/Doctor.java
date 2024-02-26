package com.example.backadmin.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String title;

    private String dob;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String gender;

    private String address;

    private String city;

    private String state;

    private String email;

    private String department;

    private String pincode;

    @Column(name = "hospital_id")
    private Long hospitalId;

}
