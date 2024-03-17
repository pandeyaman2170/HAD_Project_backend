package com.example.teleconsultationbackend.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "global_admin")
public class GlobalAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Hospital> hospitals = new ArrayList<>();
}
