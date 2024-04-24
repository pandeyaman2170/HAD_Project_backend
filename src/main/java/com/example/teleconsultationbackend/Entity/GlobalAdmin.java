package com.example.teleconsultationbackend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "global_admin")
@Schema(
        description = "Store the Global Admin details"
)
public class GlobalAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "phone",nullable = false,unique = true)
    private String phone;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "globalAdminUsername",nullable = false,unique = true)
    private String globalAdminUsername;

    @Column(name = "globalAdminPassword",nullable = false)
    private String globalAdminPassword;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Hospital> hospitals = new ArrayList<>();
    @Override
    public String toString() {
        return "GlobalAdmin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", globalAdminUsername='" + globalAdminUsername + '\'' +
                ", globalAdminPassword='" + globalAdminPassword + '\'' +
                '}';
    }
}
