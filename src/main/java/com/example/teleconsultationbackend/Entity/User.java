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

    @Column(name = "title",nullable=false)
    private String title;

    @Column(name = "first_name",nullable=false)
    private String firstName;

    @Column(name = "last_name",nullable=false)
    private String lastName;

    @Column(name = "dob",nullable=false)
    private Date dob;

    @Column(name = "role",nullable=false)
    private String role;

    @Column(name = "email",nullable=false,unique = true)
    private String email;

    @Column(name = "gender",nullable=false)
    private char gender;

    @Column(name = "phone",nullable=false,unique = true)
    private String phone;

    @Column(name = "address",nullable=false)
    private String address;

    @Column(name = "city",nullable=false)
    private String city;

    @Column(name = "pincode",nullable=false)
    private Long pincode;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Doctor doctor;


    // Getters and setters

    // Constructors

    // Other methods

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", pincode=" + pincode +
                '}';
    }
}
