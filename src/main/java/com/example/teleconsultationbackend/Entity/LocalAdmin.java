package com.example.teleconsultationbackend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class LocalAdmin {
    // Additional admin-specific attributes or methods can be added here

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}