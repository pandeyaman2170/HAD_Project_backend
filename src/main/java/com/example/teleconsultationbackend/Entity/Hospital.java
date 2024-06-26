package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "hospitals")
@Schema(
        description = "Store the Hospital details"
)
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospital_id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "hospitalUserName",nullable = false)
    private String hospitalUserName;

    @Column(name = "hospitalPassword",nullable = false)
    private String hospitalPassword;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "global_admin_id")  // Foreign key column in hospitals table
    private GlobalAdmin admin;

    @JsonIgnore
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Doctor> doctors;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Department> departments;
//    @JoinTable(name = "hospital_department",
//            joinColumns = @JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id"),
//            inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName ="id"))
//    private Set<Department> departments= new HashSet<>();;

    @Override
    public String toString() {
        return "Hospital{" +
                "hospital_id=" + hospital_id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", hospitalUserName='" + hospitalUserName + '\'' +
                ", hospitalPassword='" + hospitalPassword + '\'' +
                '}';
    }
}
