package com.example.teleconsultationbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospital_id;

    private String name;
    private String location;
    private String phone;
    private String hospitalUserName;
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
                ", admin=" + admin +
                ", doctors=" + doctors +
                ", departments=" + departments +
                '}';
    }
}
