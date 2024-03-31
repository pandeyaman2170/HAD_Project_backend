package com.example.teleconsultationbackend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Schema(
        description = "Store all user information within system"
)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String title;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Date dob;
    private String role;
    private String email;
    private char gender;
    private String phone;
    private String address;
    private String city;
    private Long pincode;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Doctor doctor;


    // Getters and setters

    // Constructors

    // Other methods
}
