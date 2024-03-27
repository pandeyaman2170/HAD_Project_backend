package com.example.teleconsultationbackend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String globalAdminUsername;
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
                ", hospitals=" + hospitals +
                '}';
    }
}
